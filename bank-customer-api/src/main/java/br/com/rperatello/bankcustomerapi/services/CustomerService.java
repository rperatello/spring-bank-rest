package br.com.rperatello.bankcustomerapi.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rperatello.bankcustomerapi.exceptions.ResourceNotFoundException;
import br.com.rperatello.bankcustomerapi.model.Customer;
import br.com.rperatello.bankcustomerapi.repositories.ICustomerRepository;

//

@Service
public class CustomerService implements ICustomerService {
	
	private Logger logger = Logger.getLogger(CustomerService.class.getName());
	
	@Autowired
	ICustomerRepository repository;

	@Override
	public Customer findById(Long id) {
		logger.info(String.format("Find customer with ID %s ...", id));
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
	}

	@Override
	public List<Customer> getAll() {
		logger.info("Get all customers ...");
		return repository.findAll();
	}

	@Override
	public Customer createNewCustomer(Customer customer) {
		logger.info("Create a new customer ...");
		return repository.save(customer);
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		logger.info(String.format("Update customer with ID %s ...", customer.getId()));
		var customerSaved = repository.findById(customer.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		customerSaved.setName(customer.getName());
		customerSaved.setDocument(customer.getDocument());
		customerSaved.setAddress(customer.getAddress());
		customerSaved.setPassword(customer.getPassword());
		
		return repository.save(customerSaved);
	}

	@Override
	public void deleteCustomer(Long id) {
		logger.info(String.format("Delete customer with ID %s ...", id));		
		var customerSaved = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		repository.delete(customerSaved);		
		return;		
	}
	
	
	

	


}
