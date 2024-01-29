package br.com.rperatello.bankcoreapi.services;

import java.util.List;

import br.com.rperatello.bankcoreapi.data.vo.v1.CustomerRequestVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.CustomerResponseVO;

public interface ICustomerService {

	CustomerResponseVO findById(Long id);
	
	CustomerResponseVO findByDocument(String document);
	
	List<CustomerResponseVO> getAll();	
	
	CustomerResponseVO createNewCustomer(CustomerRequestVO customer);
	
	CustomerResponseVO updateCustomer(CustomerRequestVO customer);
	
	CustomerResponseVO addCustomerHateoasLinks( CustomerResponseVO vo);
	
	boolean IsAValidCustomerDocument(String document);
	
	void deleteCustomer(Long id);
	
}
