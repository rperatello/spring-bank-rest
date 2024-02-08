package br.com.rperatello.bankcoreapi.model.builder;

import java.io.Console;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import br.com.rperatello.bankcoreapi.model.AccountTransactionNotification;
import br.com.rperatello.bankcoreapi.model.TransactionType;
import br.com.rperatello.bankcoreapi.services.AccountService;
import br.com.rperatello.bankcoreapi.utils.formatters.StringFormat;
import br.com.rperatello.bankcoreapi.utils.serealization.converter.GsonUtil;

public class AccountTransactionNotificationBuilder implements IAccountTransactionNotificationBuilder<AccountTransactionNotification>{

	private AccountTransactionNotification builder;

    public AccountTransactionNotificationBuilder() {
    	builder = new AccountTransactionNotification();
    	SetDefaultValues();
    }

    public AccountTransactionNotificationBuilder transactionId(Long id) {
    	if( id == null ) id = 0L;
        builder.setTransactionId(id); 
        return this;
    }

    public AccountTransactionNotificationBuilder customerDocument(String document) {
    	if( document == null ) document = "";
    	builder.setCustomerDocument(StringFormat.removeAllSpaces(document));
        return this;
    }
    
    public AccountTransactionNotificationBuilder agencyNumber(Long number) {
    	if( number == null ) number = 0L;
    	builder.setAgencyNumber(number);
        return this;
    }
    
    public AccountTransactionNotificationBuilder accountNumber(Long number) {
    	if( number == null ) number = 0L;
    	builder.setAccountNumber(number);
        return this;
    }
    
    public AccountTransactionNotificationBuilder amount(BigDecimal balance) {
    	if( balance == null ) balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    	builder.setAmount(balance);
        return this;
    }
    
    public AccountTransactionNotificationBuilder transactionType(TransactionType type) {
    	if( type == null ) return this;
    	builder.setTransactionType(type);
    	return this;
    }
    
    public AccountTransactionNotificationBuilder transactionDatetime(LocalDateTime transactionDatetime) {
    	if( transactionDatetime == null ) return this;
    	builder.setTransactionDatetime(transactionDatetime);
    	return this;
    }    
	
	@Override
	public AccountTransactionNotification build() {
		return builder;
	}
	
	private void SetDefaultValues() {
    	builder.setTransactionId(0L); 
        builder.setCustomerDocument("");
        builder.setAgencyNumber(0L);
        builder.setAccountNumber(0L);
        builder.setAmount(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
    }    

    public static AccountTransactionNotificationBuilder getbuilder() {
    	return new AccountTransactionNotificationBuilder();
    }
	
}
