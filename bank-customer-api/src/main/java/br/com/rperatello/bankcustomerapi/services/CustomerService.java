package br.com.rperatello.bankcustomerapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.rperatello.bankcustomerapi.model.Customer;

//

@Service
public class CustomerService implements ICustomerService {
	
	private Logger logger = Logger.getLogger(CustomerService.class.getName());

	@Override
	public Customer findById(Long id) {
		logger.info(String.format("Find customer with ID %s ...", id));
		return new Customer();
	}

	@Override
	public List<Customer> getAll() {
		logger.info("Get all customers ...");
		return new ArrayList<Customer>();
	}

	@Override
	public Customer createNewCustomer(Customer customer) {
		logger.info("Create a new customer ...");
		return customer;
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		logger.info(String.format("Update customer with ID %s ...", customer.getId()));		
		return customer;
	}

	@Override
	public void deleteCustomer(Long id) {
		logger.info(String.format("Delete customer with ID %s ...", id));
		return;		
	}
	
	
	

	


}
