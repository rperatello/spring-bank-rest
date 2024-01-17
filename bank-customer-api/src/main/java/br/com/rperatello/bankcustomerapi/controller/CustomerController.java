package br.com.rperatello.bankcustomerapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rperatello.bankcustomerapi.data.vo.v1.CustomerRequestVO;
import br.com.rperatello.bankcustomerapi.data.vo.v1.CustomerResponseVO;
import br.com.rperatello.bankcustomerapi.model.MediaType;
import br.com.rperatello.bankcustomerapi.services.ICustomerService;

@RestController
@RequestMapping("/api/customer/v1")
public class CustomerController {	

	
	@Autowired
	private ICustomerService customerService;
	
	@GetMapping(
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }
	)
	public List<CustomerResponseVO> getAll() {
		var res = customerService.getAll();
		return res;
	}

	@GetMapping(
			value = "/{id}",
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }
	)
	public CustomerResponseVO findById(@PathVariable(value = "id") Long id) {
		var res = customerService.findById(id);
		return res;
	}
	
	@PostMapping(
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  }
	)
	public CustomerResponseVO createNewCustomer(@RequestBody CustomerRequestVO customer) {
		var res = customerService.createNewCustomer(customer);
		return res;
	}
	
	@PutMapping(
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  }
	)
	public CustomerResponseVO updateCustomer(@RequestBody CustomerRequestVO customer) {
		var res = customerService.updateCustomer(customer);
		return res;
	}	
	
	@DeleteMapping(
			value = "/{id}"
	)
	public ResponseEntity<?> DeleteCustomer(@PathVariable(value = "id") Long id) {
		customerService.deleteCustomer(id);
		return ResponseEntity.noContent().build();
	}
	



}
