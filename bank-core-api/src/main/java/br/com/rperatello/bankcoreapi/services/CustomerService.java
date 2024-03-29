package br.com.rperatello.bankcoreapi.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rperatello.bankcoreapi.controller.CustomerController;
import br.com.rperatello.bankcoreapi.data.vo.v1.CustomerRequestVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.CustomerResponseVO;
import br.com.rperatello.bankcoreapi.exceptions.DatabaseActionException;
import br.com.rperatello.bankcoreapi.exceptions.RequiredObjectIsNullException;
import br.com.rperatello.bankcoreapi.exceptions.ResourceNotFoundException;
import br.com.rperatello.bankcoreapi.mapper.Mapper;
import br.com.rperatello.bankcoreapi.model.Customer;
import br.com.rperatello.bankcoreapi.model.builder.CustomerBuilder;
import br.com.rperatello.bankcoreapi.repositories.IAccountRepository;
import br.com.rperatello.bankcoreapi.repositories.ICustomerRepository;
import jakarta.transaction.Transactional;

//

@Service
public class CustomerService implements ICustomerService {
	
	private Logger logger = Logger.getLogger(CustomerService.class.getName());
	
	private final int[] documentLenghtAccepted = {11, 14};
	
	@Autowired
	ICustomerRepository customerRepository;
	
	@Autowired
	IAccountRepository accountRepository;

	@Override
	public CustomerResponseVO findById(Long id) {
		logger.info(String.format("Find customer with ID %s ...", id));
		var customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		var vo = Mapper.parseObject(customer, CustomerResponseVO.class);
		return addCustomerHateoasLinks(vo);
	}
	
	@Override
	public CustomerResponseVO findByDocument(String document) {
		logger.info(String.format("Find customer with document %s ...", document));
		IsAValidCustomerDocument(document);
		var customer = customerRepository.findByDocument(document);
		if (customer == null) new ResourceNotFoundException("No records found for this ID");
		var vo = Mapper.parseObject(customer, CustomerResponseVO.class);
		return addCustomerHateoasLinks(vo);
	}

	@Override
	public List<CustomerResponseVO> getAll() {
		logger.info("Get all customers ...");		
		var customers = Mapper.parseListObjects(customerRepository.findAll(), CustomerResponseVO.class);
		customers
			.stream()
			.forEach(p -> addCustomerHateoasLinks(p));
		return customers;
	}

	@Override
	public CustomerResponseVO createNewCustomer(CustomerRequestVO customerVO) {
		logger.info("Create a new customer ...");		
		var customerReceived = Mapper.parseObject(customerVO, Customer.class);
		var builder = CustomerBuilder.getbuilder();
		var customer = builder
				.id(customerReceived.getId())
				.name(customerReceived.getName())
				.document(customerReceived.getDocument())
				.address(customerReceived.getAddress())
				.password(customerReceived.getPassword())
				.build();
		validateObject(customer);
		var vo = Mapper.parseObject(customerRepository.save(customer), CustomerResponseVO.class);
		return addCustomerHateoasLinks(vo);
	}

	@Override
	@Transactional
	public CustomerResponseVO updateCustomer(CustomerRequestVO customerVO) {
		logger.info(String.format("Update customer with ID %s ...", customerVO.getId()));		
		var customerReceived = Mapper.parseObject(customerVO, Customer.class);	
		var builder = CustomerBuilder.getbuilder();
		var customer = builder
				.id(customerReceived.getId())
				.name(customerReceived.getName())
				.document(customerReceived.getDocument())
				.address(customerReceived.getAddress())
				.password(customerReceived.getPassword())
				.build();		
		validateObject(customer);		
		var customerSaved = customerRepository.findById(customer.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));		
		customerSaved = Mapper.copyProperties(customer, customerSaved.getClass());
		var vo = Mapper.parseObject(customerRepository.save(customerSaved), CustomerResponseVO.class);
		return addCustomerHateoasLinks(vo);
	}

	@Override
	public void deleteCustomer(Long id) {
		logger.info(String.format("Delete customer with ID %s ...", id));		
		var customerSaved = customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		logger.info(String.format("customerSaved: %s", customerSaved.getId()));
		var accounts = accountRepository.findByCustomerId(customerSaved.getId());
		logger.info(String.format("accounts sizes: %s", accounts.size()));
		if (!accounts.isEmpty()) throw new DatabaseActionException("Customer has associated account");
		customerRepository.delete(customerSaved);		
		return;		
	}	
	
	private boolean validateObject(Customer customer) {			
		var customerInDatabase = customerRepository.findByDocument(customer.getDocument());
		if (customerInDatabase != null && customerInDatabase.getId() != customer.getId())
			throw new DatabaseActionException("Database already contains this document");
		IsAValidCustomerDocument(customer.getDocument());
		if (customer != null && customer.getName() == "")
			throw new RequiredObjectIsNullException("A valid name is required");
		if (customer != null && customer.getAddress() == "")
			throw new RequiredObjectIsNullException("A valid address is required");
		return true;
	}
	
	public boolean IsAValidCustomerDocument(String document) {		
		if (document != null && !document.matches("\\d+"))
			throw new RequiredObjectIsNullException("A valid document is required");
		if (document != null && !Arrays.stream(documentLenghtAccepted).anyMatch(el -> el == document.length())) 
			throw new RequiredObjectIsNullException("A valid document is required");
		return true;
	}
	
	public CustomerResponseVO addCustomerHateoasLinks( CustomerResponseVO vo) {		
		try {
			if (vo == null) throw new ResourceNotFoundException("Customer data is required");
			return vo.add(linkTo(methodOn(CustomerController.class).findById(vo.getKey())).withSelfRel());
		} 
		catch (Exception e) {
			logger.log(Level.SEVERE, String.format("addCustomerHateoasLinks - Error: %s ", e.getMessage()));
			return vo;
		}
	}

}
