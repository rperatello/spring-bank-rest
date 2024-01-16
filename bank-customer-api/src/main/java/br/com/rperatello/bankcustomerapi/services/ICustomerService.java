package br.com.rperatello.bankcustomerapi.services;

import java.util.List;

import br.com.rperatello.bankcustomerapi.model.Customer;

public interface ICustomerService {

	Customer findById(Long id);
	
	List<Customer> getAll();	
	
	Customer createNewCustomer(Customer customer);
	
	Customer updateCustomer(Customer customer);
	
	void deleteCustomer(Long id);
	
}
