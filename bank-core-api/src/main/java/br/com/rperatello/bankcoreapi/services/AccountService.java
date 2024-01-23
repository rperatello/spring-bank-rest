package br.com.rperatello.bankcoreapi.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rperatello.bankcoreapi.controller.AccountController;
import br.com.rperatello.bankcoreapi.controller.AgencyController;
import br.com.rperatello.bankcoreapi.controller.CustomerController;
import br.com.rperatello.bankcoreapi.data.vo.v1.AccountRequestVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.AccountResponseVO;
import br.com.rperatello.bankcoreapi.exceptions.DatabaseActionException;
import br.com.rperatello.bankcoreapi.exceptions.RequiredObjectIsNullException;
import br.com.rperatello.bankcoreapi.exceptions.ResourceNotFoundException;
import br.com.rperatello.bankcoreapi.mapper.Mapper;
import br.com.rperatello.bankcoreapi.model.Account;
import br.com.rperatello.bankcoreapi.model.builder.AccountBuilder;
import br.com.rperatello.bankcoreapi.repositories.IAccountRepository;
import br.com.rperatello.bankcoreapi.repositories.IAgencyRepository;
import br.com.rperatello.bankcoreapi.repositories.ICustomerRepository;
import br.com.rperatello.bankcoreapi.utils.serealization.converter.GsonUtil;

//

@Service
public class AccountService implements IAccountService {
	
	private Logger logger = Logger.getLogger(AccountService.class.getName());
	
	@Autowired
	IAccountRepository accountRepository;
	
	@Autowired
	IAgencyRepository agencyRepository;
	
	@Autowired
	ICustomerRepository customerRepository;
	
	@Autowired
	ICustomerService customerService;

	@Override
	public AccountResponseVO findById(Long id) {
		logger.info(String.format("Find account with ID %s ...", id));
		var account = accountRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		var vo = Mapper.parseObject(account, AccountResponseVO.class);
		var agencyVO = vo.getAgency().add(linkTo(methodOn(AgencyController.class).findById(vo.getAgency().getKey())).withSelfRel());
		vo.setAgency(agencyVO);
		var customerVO = vo.getCustomer().add(linkTo(methodOn(CustomerController.class).findById(vo.getCustomer().getKey())).withSelfRel());
		vo.setCustomer(customerVO);
		vo.add(linkTo(methodOn(AccountController.class).findById(id)).withSelfRel());
		return vo;
	}

	@Override
	public List<AccountResponseVO> getAll() {
		logger.info("Get all accounts ...");		
		var accounts = Mapper.parseListObjects(accountRepository.findAll(), AccountResponseVO.class);
		accounts
			.stream()
			.forEach(p -> {
				var agencyVO = p.getAgency().add(linkTo(methodOn(AgencyController.class).findById(p.getAgency().getKey())).withSelfRel());
				p.setAgency(agencyVO);
				var customerVO = p.getCustomer().add(linkTo(methodOn(CustomerController.class).findById(p.getCustomer().getKey())).withSelfRel());
				p.setCustomer(customerVO);
				p.add(linkTo(methodOn(AccountController.class).findById(p.getKey())).withSelfRel());
			});
		return accounts;
	}

	@Override
	public AccountResponseVO createNewAccount(AccountRequestVO accountVO) {
		logger.info("Create a new account ...");		
		var accountReceived = Mapper.parseObject(accountVO, Account.class);
		var agency = agencyRepository.findByNumber(accountReceived.getAgencyNumber());
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
		validateObject(account);
		var vo = Mapper.parseObject(accountRepository.save(account), AccountResponseVO.class);
		var agencyVO = vo.getAgency().add(linkTo(methodOn(AgencyController.class).findById(vo.getAgency().getKey())).withSelfRel());
		vo.setAgency(agencyVO);
		var customerVO = vo.getCustomer().add(linkTo(methodOn(CustomerController.class).findById(vo.getCustomer().getKey())).withSelfRel());
		vo.setCustomer(customerVO);
		vo.add(linkTo(methodOn(AccountController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	@Override
	public AccountResponseVO updateAccount(AccountRequestVO accountVO) {
		logger.info(String.format("Update account with ID %s ...", accountVO.getId()));		
		var accountReceived = Mapper.parseObject(accountVO, Account.class);
		var agency = agencyRepository.findByNumber(accountReceived.getAgencyNumber());
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
		validateObject(account);		
		var accountSaved = accountRepository.findById(account.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));		
		accountSaved = Mapper.copyProperties(account, accountSaved.getClass());
		var vo = Mapper.parseObject(accountRepository.save(accountSaved), AccountResponseVO.class);
		var agencyVO = vo.getAgency().add(linkTo(methodOn(AgencyController.class).findById(vo.getAgency().getKey())).withSelfRel());
		vo.setAgency(agencyVO);
		var customerVO = vo.getCustomer().add(linkTo(methodOn(CustomerController.class).findById(vo.getCustomer().getKey())).withSelfRel());
		vo.setCustomer(customerVO);
		vo.add(linkTo(methodOn(AccountController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	@Override
	public void deleteAccount(Long id) {
		logger.info(String.format("Delete account with ID %s ...", id));
		var accountSaved = accountRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		accountRepository.delete(accountSaved);		
		return;		
	}	
	
	private boolean validateObject(Account account) {	
		
		var accountInDatabaseByNumber = accountRepository.findByNumber(account.getNumber());
		var accountInDatabaseById = accountRepository.findById(account.getId()).orElse(null);
		if (accountInDatabaseByNumber != null && accountInDatabaseByNumber.getId() != account.getId())
			throw new DatabaseActionException("Database already contains this account");
		if (account != null && !account.getNumber().toString().matches("\\d+"))
			throw new RequiredObjectIsNullException("A valid account number is required");		
		if (accountInDatabaseById != null && accountInDatabaseById.getNumber() != account.getNumber())
			throw new DatabaseActionException("Account number cannot be changed");
		if (accountInDatabaseById != null && !accountInDatabaseById.getAgency().equals(account.getAgency()))
			throw new DatabaseActionException("Agency number cannot be changed");
		return true;
	}
	
	


}
