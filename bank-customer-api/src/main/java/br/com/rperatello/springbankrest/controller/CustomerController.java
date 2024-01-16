package br.com.rperatello.springbankrest.controller;

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

import br.com.rperatello.springbankrest.model.Customer;
import br.com.rperatello.springbankrest.model.ICustomerService;
import br.com.rperatello.springbankrest.model.MediaType;

@RestController
@RequestMapping("/api/customer/v1")
public class CustomerController {	

	
	@Autowired
	private ICustomerService customerService;
	
	@GetMapping(
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }
	)
	public List<Customer> getAll() {
		var res = customerService.getAll();
		return res;
	}

	@GetMapping(
			value = "/{id}",
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }
	)
	public Customer findById(@PathVariable(value = "id") Long id) {
		var res = customerService.findById(id);
		return res;
	}
	
	@PostMapping(
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  }
	)
	public Customer createNewCustomer(@RequestBody Customer customer) {
		var res = customerService.createNewCustomer(customer);
		return res;
	}
	
	@PutMapping(
			value = "/{id}",
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  }
	)
	public Customer updateCustomer(@RequestBody Customer customer) {
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
