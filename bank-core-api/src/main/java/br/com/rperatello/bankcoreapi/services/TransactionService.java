package br.com.rperatello.bankcoreapi.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rperatello.bankcoreapi.controller.AccountController;
import br.com.rperatello.bankcoreapi.controller.TransactionController;
import br.com.rperatello.bankcoreapi.data.vo.v1.TransactionRequestVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.TransactionResponseVO;
import br.com.rperatello.bankcoreapi.exceptions.DatabaseActionException;
import br.com.rperatello.bankcoreapi.exceptions.InvalidRequestException;
import br.com.rperatello.bankcoreapi.exceptions.RequiredObjectIsNullException;
import br.com.rperatello.bankcoreapi.exceptions.ResourceNotFoundException;
import br.com.rperatello.bankcoreapi.mapper.Mapper;
import br.com.rperatello.bankcoreapi.model.Account;
import br.com.rperatello.bankcoreapi.model.Transaction;
import br.com.rperatello.bankcoreapi.model.TransactionMethod;
import br.com.rperatello.bankcoreapi.repositories.IAccountRepository;
import br.com.rperatello.bankcoreapi.repositories.IAgencyRepository;
import br.com.rperatello.bankcoreapi.repositories.ICustomerRepository;
import br.com.rperatello.bankcoreapi.repositories.ITransactionRepository;
import jakarta.transaction.Transactional;

//

@Service
public class TransactionService implements ITransactionService {	
	
	@Autowired
	IAccountRepository accountRepository;
	
	@Autowired
	IAgencyRepository agencyRepository;
	
	@Autowired
	ICustomerRepository customerRepository;
	
	@Autowired
	ITransactionRepository transactionRepository;
	
	@Autowired
	ICustomerService customerService;
	
	@Autowired
	IAgencyService agencyService;
	
	@Autowired
	IAccountService accountService;

	private Logger logger = Logger.getLogger(TransactionService.class.getName());
	
	@Override
	public TransactionResponseVO findById(Long id) {
		logger.info(String.format("Find transaction with ID %s ...", id));
		var transaction = transactionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		var vo = Mapper.parseObject(transaction, TransactionResponseVO.class);
		vo.add(linkTo(methodOn(AccountController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	@Override
	@Transactional
	public TransactionResponseVO createNewTransaction(TransactionRequestVO requestVO) {
		logger.info("Create a new transaction ...");
		var transactionReceived = Mapper.parseObject(requestVO, Transaction.class);		
		Account payer = null;
		if(transactionReceived.getPayerBalanceModel() != null && !transactionReceived.getTransactionMethod().equals(TransactionMethod.DEPOSIT)) {
			payer = accountRepository.findByAgencyNumberAndNumber(transactionReceived.getPayerBalanceModel().getAgencyNumber(), transactionReceived.getPayerBalanceModel().getAccountNumber());
			transactionReceived.setPayer(payer);
			transactionReceived.getPayerBalanceModel().setAccount(payer);
			accountService.updateAccountBalance(transactionReceived.getPayerBalanceModel());
		}
		var payee = accountRepository.findByAgencyNumberAndNumber(transactionReceived.getPayeeBalanceModel().getAgencyNumber(), transactionReceived.getPayeeBalanceModel().getAccountNumber());
		transactionReceived.setPayee(payee);
		transactionReceived.getPayeeBalanceModel().setAccount(payee);
		accountService.updateAccountBalance(transactionReceived.getPayeeBalanceModel());
		
		var vo = Mapper.parseObject(transactionRepository.save(transactionReceived), TransactionResponseVO.class);
		vo.add(linkTo(methodOn(TransactionController.class).findById(vo.getKey())).withSelfRel());
		return  vo;
	}

}
