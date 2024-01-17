package br.com.rperatello.bankcustomerapi.services;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rperatello.bankcustomerapi.data.vo.v1.CustomerRequestVO;
import br.com.rperatello.bankcustomerapi.data.vo.v1.CustomerResponseVO;
import br.com.rperatello.bankcustomerapi.exceptions.DatabaseActionException;
import br.com.rperatello.bankcustomerapi.exceptions.RequiredObjectIsNullException;
import br.com.rperatello.bankcustomerapi.exceptions.ResourceNotFoundException;
import br.com.rperatello.bankcustomerapi.mapper.Mapper;
import br.com.rperatello.bankcustomerapi.model.Customer;
import br.com.rperatello.bankcustomerapi.model.builder.CustomerBuilder;
import br.com.rperatello.bankcustomerapi.repositories.ICustomerRepository;

//

@Service
public class CustomerService implements ICustomerService {
	
	private Logger logger = Logger.getLogger(CustomerService.class.getName());
	
	@Autowired
	ICustomerRepository repository;

	@Override
	public CustomerResponseVO findById(Long id) {
		logger.info(String.format("Find customer with ID %s ...", id));
		var customer = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		var vo = Mapper.parseObject(customer, CustomerResponseVO.class);
		return vo;
	}

	@Override
	public List<CustomerResponseVO> getAll() {
		logger.info("Get all customers ...");		
		var customers = Mapper.parseListObjects(repository.findAll(), CustomerResponseVO.class);
		return customers;
	}

	@Override
	public CustomerResponseVO createNewCustomer(CustomerRequestVO customerVO) {
		logger.info("Create a new customer ...");		
		var customerReceived = Mapper.parseObject(customerVO, Customer.class);
		var customer = CustomerBuilder.getbuilder()
				.id(customerReceived.getId())
				.name(customerReceived.getName())
				.document(customerReceived.getDocument())
				.address(customerReceived.getAddress())
				.password(customerReceived.getPassword())
				.build();
		validateObject(customer);
		var vo = Mapper.parseObject(repository.save(customer), CustomerResponseVO.class);
		return vo;
	}

	@Override
	public CustomerResponseVO updateCustomer(CustomerRequestVO customerVO) {
		logger.info(String.format("Update customer with ID %s ...", customerVO.getId()));		
		var customerReceived = Mapper.parseObject(customerVO, Customer.class);	
		var customer = CustomerBuilder.getbuilder()
				.id(customerReceived.getId())
				.name(customerReceived.getName())
				.document(customerReceived.getDocument())
				.address(customerReceived.getAddress())
				.password(customerReceived.getPassword())
				.build();

		logger.info(String.format("password ...", customer.getPassword()));	
		validateObject(customer);		
		var customerSaved = repository.findById(customer.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));		
		customerSaved = Mapper.copyProperties(customer, customerSaved.getClass());
		var vo = Mapper.parseObject(repository.save(customerSaved), CustomerResponseVO.class);
		return vo;
	}

	@Override
	public void deleteCustomer(Long id) {
		logger.info(String.format("Delete customer with ID %s ...", id));		
		var customerSaved = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(customerSaved);		
		return;		
	}	
	
	private boolean validateObject(Customer customer) {	
		
		int[] documentLenghtAccepted = {11, 14};
		
		var customerInDatabase = repository.findByDocument(customer.getDocument());
		if (customerInDatabase != null && customerInDatabase.getId() != customer.getId())
			throw new DatabaseActionException("Database already contains this document");
		if (customer != null && !customer.getDocument().matches("\\d+"))
			throw new RequiredObjectIsNullException("A valid document is required");
		if (customer != null && !Arrays.stream(documentLenghtAccepted).anyMatch(el -> el == customer.getDocument().length())) 
			throw new RequiredObjectIsNullException("A valid document is required length " + customer.getDocument().length());
		if (customer != null && customer.getName() == "")
			throw new RequiredObjectIsNullException("A valid name is required");
		if (customer != null && customer.getAddress() == "")
			throw new RequiredObjectIsNullException("A valid address is required");
		return true;
	}


}
