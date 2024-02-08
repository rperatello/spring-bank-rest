package br.com.rperatello.bankcoreapi.model.builder;

import br.com.rperatello.bankcoreapi.model.Customer;
import br.com.rperatello.bankcoreapi.utils.formatters.StringFormat;

public class CustomerBuilder implements ICustomerBuilder<Customer> {
	
	private Customer builder;

    public CustomerBuilder() {
    	builder = new Customer();
    	SetDefaultValues();
    }

    public CustomerBuilder id(Long id) {
        builder.setId(id); 
        return this;
    }

    public CustomerBuilder name(String name) {
    	if( name == null ) name = "";
    	builder.setName(StringFormat.removeDoubleSpace(name).toUpperCase());
        return this;
    }

    public CustomerBuilder document(String document) {
    	if( document == null ) document = "";
    	builder.setDocument(StringFormat.removeAllSpaces(document));
        return this;
    }

    public CustomerBuilder address(String address) {
    	if( address == null ) address = "";
        builder.setAddress(StringFormat.removeDoubleSpace(address).toUpperCase());
        return this;
    }

    public CustomerBuilder password(String password) {
    	if( password == null ) password = "";
    	builder.setPassword(StringFormat.removeDoubleSpace(password));
        return this;
    }

    @Override
    public Customer build() {
        return builder;
    }
    
    private void SetDefaultValues() {
    	builder.setId(0L); 
        builder.setName("");
        builder.setDocument("");
        builder.setAddress("");
        builder.setPassword("");
    }    

    public static CustomerBuilder getbuilder() {
    	return new CustomerBuilder();
    }

}

