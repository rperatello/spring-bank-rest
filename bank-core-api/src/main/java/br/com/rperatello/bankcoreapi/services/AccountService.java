package br.com.rperatello.bankcoreapi.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rperatello.bankcoreapi.controller.AccountController;
import br.com.rperatello.bankcoreapi.data.vo.v1.AccountStatusRequestVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.AccountResponseVO;
import br.com.rperatello.bankcoreapi.exceptions.DatabaseActionException;
import br.com.rperatello.bankcoreapi.exceptions.InvalidRequestException;
import br.com.rperatello.bankcoreapi.exceptions.RequiredObjectIsNullException;
import br.com.rperatello.bankcoreapi.exceptions.ResourceNotFoundException;
import br.com.rperatello.bankcoreapi.mapper.Mapper;
import br.com.rperatello.bankcoreapi.model.Account;
import br.com.rperatello.bankcoreapi.model.AccountBalanceUpdateModel;
import br.com.rperatello.bankcoreapi.model.AccountUpdatesAllowed;
import br.com.rperatello.bankcoreapi.model.TransactionType;
import br.com.rperatello.bankcoreapi.model.builder.AccountBuilder;
import br.com.rperatello.bankcoreapi.repositories.IAccountRepository;
import br.com.rperatello.bankcoreapi.repositories.IAgencyRepository;
import br.com.rperatello.bankcoreapi.repositories.ICustomerRepository;
import br.com.rperatello.bankcoreapi.utils.converters.EnumConverter;
import br.com.rperatello.bankcoreapi.utils.serealization.converter.GsonUtil;
import br.com.rperatello.bankcoreapi.utils.validators.EnumValidator;
import jakarta.transaction.Transactional;


@Service
public class AccountService implements IAccountService {	
	
	@Autowired
	IAccountRepository accountRepository;
	
	@Autowired
	IAgencyRepository agencyRepository;
	
	@Autowired
	ICustomerRepository customerRepository;
	
	@Autowired
	ICustomerService customerService;
	
	@Autowired
	IAgencyService agencyService;

	private Logger logger = Logger.getLogger(AccountService.class.getName());
	
	@Override
	public AccountResponseVO findById(Long id) {
		logger.info(String.format("Find account with ID %s ...", id));
		var account = accountRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		var vo = Mapper.parseObject(account, AccountResponseVO.class);
		vo.setAgency(agencyService.addAgencyHateoasLinks(vo.getAgency()));
		vo.setCustomer(customerService.addCustomerHateoasLinks(vo.getCustomer()));
		vo.add(linkTo(methodOn(AccountController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	@Override
	public List<AccountResponseVO> getAll() {
		logger.info("Get all accounts ...");		
		var accounts = Mapper.parseListObjects(accountRepository.findAll(), AccountResponseVO.class);
		accounts
			.stream()
			.forEach(p -> {
				p.setAgency(agencyService.addAgencyHateoasLinks(p.getAgency()));
				p.setCustomer(customerService.addCustomerHateoasLinks(p.getCustomer()));
				p.add(linkTo(methodOn(AccountController.class).findById(p.getKey())).withSelfRel());
			});
		return accounts;
	}

	@Override
	public AccountResponseVO createNewAccount(AccountStatusRequestVO accountVO) {
		logger.info("Create a new account ...");		
		var accountReceived = Mapper.parseObject(accountVO, Account.class);
		var account = processAccountModel(accountReceived);
		var vo = Mapper.parseObject(accountRepository.save(account), AccountResponseVO.class);
		vo.setAgency(agencyService.addAgencyHateoasLinks(vo.getAgency()));
		vo.setCustomer(customerService.addCustomerHateoasLinks(vo.getCustomer()));
		vo.add(linkTo(methodOn(AccountController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	@Override
	@Transactional
	public AccountResponseVO updateAccountStatus(AccountStatusRequestVO accountVO) {	
		logger.info(String.format("Update account with ID %s ...", accountVO.getId()));		
		var accountReceived = Mapper.parseObject(accountVO, Account.class);
		var account = processAccountModel(accountReceived);
		var vo = Mapper.parseObject(updateAccount(account, AccountUpdatesAllowed.STATUS), AccountResponseVO.class);
		vo.setAgency(agencyService.addAgencyHateoasLinks(vo.getAgency()));
		vo.setCustomer(customerService.addCustomerHateoasLinks(vo.getCustomer()));
		vo.add(linkTo(methodOn(AccountController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}	

	@Override
	@Transactional
	public boolean updateAccountBalance(AccountBalanceUpdateModel model) {
		logger.info(String.format("Update balance - Agency: %s | Account: %s ...", model.getAgencyNumber(), model.getAccountNumber()));		
//		validateAccountBalanceUpdateModel(model);		
		
		if (model.getAccount() == null) throw new ResourceNotFoundException("Account not found");
		var account = model.getAccount();		
		if (!account.getIsActive()) throw new InvalidRequestException(String.format("Inactive account (Agency: %s | Account number: %s)", model.getAgencyNumber(), model.getAccountNumber()));
		account.setAgencyNbr(model.getAgencyNumber());
		account.setNumber(model.getAccountNumber());
		if (!account.getCustomer().getDocument().toString().trim().toLowerCase().equals((model.getDocument().toString()).trim().toLowerCase())) throw new InvalidRequestException("Document does not match");
		
		if (model.getTransactionType() != TransactionType.CREDIT && !account.getCustomer().getPassword().toString().trim().toLowerCase().equals((model.getPassword()).trim().toLowerCase())) throw new InvalidRequestException("Invalid Password");
		var accountBalance = account.getBalance();
		accountBalance = model.getTransactionType() == TransactionType.DEBIT ? accountBalance.subtract(model.getAmount()) : accountBalance.add(model.getAmount());
		if (model.getTransactionType() != TransactionType.CREDIT && accountBalance.compareTo(BigDecimal.ZERO) < 0) throw new InvalidRequestException("Insufficient funds");
		account.setBalance(accountBalance);		
		updateAccount(account, AccountUpdatesAllowed.BALANCE);
		model.setAccount(account);
		return true;
	}
	
	@Override
	public void deleteAccount(Long id) {
		logger.info(String.format("Delete account with ID %s ...", id));
		var accountSaved = accountRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		accountRepository.delete(accountSaved);		
		return;		
	}
	
	private Account processAccountModel(Account accountReceived) {		
		var agency = agencyRepository.findByNumber(accountReceived.getAgencyNbr());
		if (agency == null) throw new RequiredObjectIsNullException("A valid agency number is required");
		accountReceived.setAgency(agency);		
		if(!customerService.IsAValidCustomerDocument(accountReceived.getCustomerNumber())) throw new RequiredObjectIsNullException("A valid customer number is required");
		var customer = customerRepository.findByDocument(accountReceived.getCustomerNumber());
		if (customer == null) throw new RequiredObjectIsNullException("A customer number registered is required");
		accountReceived.setCustomer(customer);		
		var builder = AccountBuilder.getbuilder();
		var account = builder		
				.id(accountReceived.getId())
				.number(accountReceived.getNumber())
				.balance(accountReceived.getBalance())
				.isActive(accountReceived.getIsActive())
				.agency(accountReceived.getAgency())
				.customer(customer)
				.build();
		logger.info(String.format("account: %s", GsonUtil.Serialize(account)));
		validateAccountModel(account);
		return account;
	}	

	private Account updateAccount(Account accountToUpdate, String propertyToUpdate) {		
		if(accountToUpdate == null) throw new RequiredObjectIsNullException("account is required");
		if(propertyToUpdate == null) throw new RequiredObjectIsNullException("account property is required to updates");		
		var accountSaved = accountRepository.findByAgencyNumberAndNumber(accountToUpdate.getAgencyNbr(), accountToUpdate.getNumber());		
		if (accountSaved == null) throw new ResourceNotFoundException("Account not found");		
		switch (propertyToUpdate) {
        case AccountUpdatesAllowed.BALANCE:
        	accountSaved.setBalance(accountToUpdate.getBalance());
            break;
        case AccountUpdatesAllowed.CUSTOMER:
        	accountSaved.setCustomer(accountToUpdate.getCustomer());
            break;
        case AccountUpdatesAllowed.STATUS:
        	accountSaved.setIsActive(accountToUpdate.getIsActive());
        	break;
        default:
        	logger.log(Level.WARNING, String.format("updateAccount - Propert not found: %s", propertyToUpdate));
            break;
        }
    	return accountRepository.save(accountSaved);
	}

	
	private boolean validateAccountModel(Account account) {		
		var accountInDatabaseByNumber = accountRepository.findByAgencyNumberAndNumber(account.getAgencyNbr(), account.getNumber());
		var accountInDatabaseById = accountRepository.findById(account.getId()).orElse(null);
		if (accountInDatabaseByNumber != null && accountInDatabaseByNumber.getId() != account.getId())
			throw new DatabaseActionException("Database already contains this account");
		if (account != null && !account.getNumber().toString().matches("\\d+"))
			throw new RequiredObjectIsNullException("A valid account number is required");	
		if (accountInDatabaseById != null && accountInDatabaseById.getNumber().longValue() != account.getNumber().longValue())
			throw new DatabaseActionException("Account number cannot be changed");
		if (accountInDatabaseById != null && accountInDatabaseById.getAgency().getNumber().longValue() != account.getAgency().getNumber().longValue())
			throw new DatabaseActionException("Agency number cannot be changed");
		return true;
	}		
	
	private boolean validateAccountBalanceUpdateModel(AccountBalanceUpdateModel model) {
		if (model.getAgencyNumber() == null) throw new InvalidRequestException("Agency number is required");
		if (model.getAccountNumber() == null) throw new InvalidRequestException("Account number is required");
		if (model.getTransactionType() == null) throw new InvalidRequestException("Transaction type is required");		
		if (!EnumValidator.hasValue(TransactionType.class, model.getTransactionType())) throw new InvalidRequestException("Invalid transaction type");		
		model.setTransactionType(EnumConverter.convertToEnum(TransactionType.class, model.getTransactionType()));			
		if (model.getTransactionType() == TransactionType.DEBIT && model.getPassword() == null) throw new InvalidRequestException("Password is required");
		return true;
	}


}
