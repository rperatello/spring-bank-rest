package br.com.rperatello.bankcoreapi.model.builder;

import java.math.BigDecimal;

import br.com.rperatello.bankcoreapi.model.Account;
import br.com.rperatello.bankcoreapi.model.AccountStatus;
import br.com.rperatello.bankcoreapi.model.Agency;
import br.com.rperatello.bankcoreapi.model.Customer;
import br.com.rperatello.bankcoreapi.utils.formatters.StringFormat;

public class AccountBuilder implements IAccountBuilder<Account> {
	
	private Account builder;

    public AccountBuilder() {
    	builder = new Account();
    	SetDefaultValues();
    }

    public AccountBuilder id(Long id) {
    	if( id == null ) id = 0L;
        builder.setId(id); 
        return this;
    }

    public AccountBuilder number(Long number) {
    	if( number == null ) number = 0L;
    	builder.setNumber(number);
        return this;
    }
    
    public AccountBuilder balance(BigDecimal balance) {
    	if( balance == null ) balance = BigDecimal.ZERO;
    	builder.setBalance(balance);
        return this;
    }
    
    public AccountBuilder isActive(Boolean isActive) {
    	if ( isActive == null ) isActive = false;    	
    	if ( isActive ) builder.setStatus(AccountStatus.ACTIVE);
    	else
    		builder.setStatus(AccountStatus.INACTIVE);    	
    	builder.setIsActive(isActive);
        return this;
    }
    
    public AccountBuilder agency(Agency agency) {
    	var agencyBuilder = AgencyBuilder.getbuilder();
    	if ( agency == null ) builder.setAgency(agencyBuilder.build());
    	else {
    		agencyBuilder
	    		.id(agency.getId())
				.name(agency.getName())
				.number(agency.getNumber())
				.build();
    		builder.setAgency(agencyBuilder.build());
    		builder.setAgencyNbr(agency.getNumber());
    	}
        return this;
    }
    
    public AccountBuilder customer(Customer customer) {
    	var customerBuilder = CustomerBuilder.getbuilder();
    	if ( customer == null ) builder.setCustomer(customerBuilder.build());
    	else {
    		customerBuilder
	    		.id(customer.getId())
				.name(customer.getName())
				.document(customer.getDocument())
				.address(customer.getAddress())
				.password(customer.getPassword())
				.build();
    		builder.setCustomer(customerBuilder.build());
    		builder.setCustomerNumber(customer.getDocument());
    	}
        return this;
    } 
    
    public AccountBuilder agencyNumber(Long number) {
    	if( number == null ) number = 0L;
    	builder.setAgencyNbr(number);
        return this;
    }
    
    public AccountBuilder accountNumber(Long number) {
    	if( number == null ) number = 0L;
    	builder.setNumber(number);
        return this;
    } 
    
    public AccountBuilder customerDocument(String document) {
    	if( document == null ) document = "";
    	builder.setCustomerNumber(StringFormat.removeAllSpaces(document));
        return this;
    }   

    @Override
    public Account build() {
        return builder;
    }
    
    private void SetDefaultValues() {
    	builder.setId(0L); 
        builder.setNumber(0L);
        builder.setBalance(BigDecimal.ZERO);
        builder.setIsActive(false);
        builder.setAgency(AgencyBuilder.getbuilder().build());      
        builder.setCustomer(CustomerBuilder.getbuilder().build()); 
    }    

    public static AccountBuilder getbuilder() {
    	return new AccountBuilder();
    }

}

