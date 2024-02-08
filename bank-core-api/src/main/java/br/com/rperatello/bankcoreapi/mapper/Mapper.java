package br.com.rperatello.bankcoreapi.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

import br.com.rperatello.bankcoreapi.data.vo.v1.AccountStatusRequestVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.AccountTransactionBasicResponseVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.AccountTransactionNotificationVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.AccountDataModelVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.AccountResponseVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.AgencyResponseVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.CustomerResponseVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.TransactionRequestVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.TransactionResponseVO;
import br.com.rperatello.bankcoreapi.model.Account;
import br.com.rperatello.bankcoreapi.model.AccountBalanceUpdateModel;
import br.com.rperatello.bankcoreapi.model.AccountTransactionNotification;
import br.com.rperatello.bankcoreapi.model.Agency;
import br.com.rperatello.bankcoreapi.model.Customer;
import br.com.rperatello.bankcoreapi.model.Transaction;
import br.com.rperatello.bankcoreapi.model.TransactionType;
import br.com.rperatello.bankcoreapi.utils.serealization.converter.GsonUtil;

public class Mapper {
	
	private static Logger logger = Logger.getLogger(Mapper.class.getName());

	private static ModelMapper mapper = new ModelMapper();
	
	static {
		mapper.getConfiguration().setAmbiguityIgnored(true);
		
		mapper.createTypeMap(Customer.class, CustomerResponseVO.class)
			.addMapping(Customer::getId, CustomerResponseVO::setKey);
		
		mapper.createTypeMap(Agency.class, AgencyResponseVO.class)
		.addMapping(Agency::getId, AgencyResponseVO::setKey);
		
		mapper.createTypeMap(Account.class, AccountResponseVO.class)
		.addMapping(Account::getId, AccountResponseVO::setKey);
		
		mapper.createTypeMap(Account.class, AccountTransactionBasicResponseVO.class)
		.addMapping(Account::getId, AccountTransactionBasicResponseVO::setKey);
		
		mapper.createTypeMap(AccountStatusRequestVO.class, Account.class)
		.addMapping(AccountStatusRequestVO::getAgencyNbr, Account::setAgencyNbr)		
		.addMapping(AccountStatusRequestVO::getCustomerNumber, Account::setCustomerNumber);	
		
		mapper.createTypeMap(AccountDataModelVO.class, AccountDataModelVO.class);
		
		try {
			mapper.createTypeMap(TransactionRequestVO.class, Transaction.class)		
			.setConverter(new Converter<TransactionRequestVO, Transaction>() {
				@Override
	            public Transaction convert(MappingContext<TransactionRequestVO, Transaction> context) {
	            	Transaction transaction = new Transaction();
	            	AccountDataModelVO payerVO = context.getSource().getPayer().orElse(null);
	            	Optional<AccountBalanceUpdateModel> payer = Optional.empty();
	            	if (payerVO != null && !context.getSource().getPayer().isEmpty()) {
	            		var payerParsed = Mapper.parseObject(payerVO, AccountBalanceUpdateModel.class);
	            		payerParsed.setAmount(context.getSource().getAmount() == null ? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP) : context.getSource().getAmount());
	            		payerParsed.setTransactionType(TransactionType.DEBIT);
	            		payer = Optional.ofNullable(payerParsed);
	            	}
	            	transaction.setPayerBalanceModel(payer.orElse(null));
	            	AccountBalanceUpdateModel payee = null;
	            	if (context.getSource().getPayee() != null) {
	            		payee = Mapper.parseObject(context.getSource().getPayee(), AccountBalanceUpdateModel.class);
	            		payee.setAmount(context.getSource().getAmount() == null ? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP) : context.getSource().getAmount());
		            	payee.setTransactionType(TransactionType.CREDIT);
	            	}
	            	transaction.setPayeeBalanceModel(payee);
	            	transaction.setAmount(context.getSource().getAmount() == null ? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP) : context.getSource().getAmount());
	            	transaction.setTransactionMethod(context.getSource().getTransactionMethod());
	            	transaction.setDatetime(LocalDateTime.now());
	            	var request = Mapper.parseObject(context.getSource(), TransactionRequestVO.class);
	            	request.getPayer().ifPresent( v -> v.setPassword("****"));
	            	request.getPayee().setPassword("****");
	            	transaction.setRequest(GsonUtil.Serialize(request).toString());
	                return transaction;
	            }
	        });			
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		
		mapper.createTypeMap(Transaction.class, TransactionResponseVO.class)
			.addMapping(Transaction::getId, TransactionResponseVO::setKey);
		
		mapper.createTypeMap(AccountTransactionNotification.class, AccountTransactionNotificationVO.class);
		
	}
	
	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}
	
	public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
		List<D> destinationObjects = new ArrayList<D>();
		for (O o : origin) {
			destinationObjects.add(mapper.map(o, destination));
		}
		return destinationObjects;
	}
	
	public static <O, D> D copyProperties (O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}	

}