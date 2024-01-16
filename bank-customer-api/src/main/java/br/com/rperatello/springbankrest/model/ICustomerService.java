package br.com.rperatello.springbankrest.model;

import java.util.List;

public interface ICustomerService {

	Customer findById(Long id);
	
	List<Customer> getAll();	
	
	Customer createNewCustomer(Customer customer);
	
	Customer updateCustomer(Customer customer);
	
	void deleteCustomer(Long id);
	
}
