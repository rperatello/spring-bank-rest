package br.com.rperatello.bankcoreapi.services;

import java.util.List;

import br.com.rperatello.bankcoreapi.data.vo.v1.CustomerRequestVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.CustomerResponseVO;

public interface ICustomerService {

	CustomerResponseVO findById(Long id);
	
	List<CustomerResponseVO> getAll();	
	
	CustomerResponseVO createNewCustomer(CustomerRequestVO customer);
	
	CustomerResponseVO updateCustomer(CustomerRequestVO customer);
	
	void deleteCustomer(Long id);
	
	boolean IsAValidCustomerDocument(String document);
	
}
