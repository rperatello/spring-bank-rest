package br.com.rperatello.bankcustomerapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/customer/v1")
@Tag(name = "Customer", description = "Endpoints for Managing Customers")
public class CustomerController {	

	
	@Autowired
	private ICustomerService customerService;
	
	@GetMapping(
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }
	)
	@Operation(
		summary = "Get all customers", description = "Finds all customers",
		tags = {"Customer"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200",
				content = {
					@Content(
						mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = CustomerResponseVO.class))
					)
			}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public List<CustomerResponseVO> getAll() {
		var res = customerService.getAll();
		return res;
	}

	@GetMapping(
			value = "/{id}",
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }
	)
	@Operation(
		summary = "Finds a customer", description = "Finds a customer",
		tags = {"Customer"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200",
				content = @Content(schema = @Schema(implementation = CustomerResponseVO.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public CustomerResponseVO findById(@PathVariable(value = "id") Long id) {
		var res = customerService.findById(id);
		return res;
	}
	
	@PostMapping(
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  }
	)
	@Operation(
			summary = "Adds a new customer", description = "Adds a new customer using JSON, XML or YML representation",
			tags = {"Customer"},
			responses = {
				@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = CustomerResponseVO.class))
				),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public CustomerResponseVO createNewCustomer(@RequestBody CustomerRequestVO customer) {
		var res = customerService.createNewCustomer(customer);
		return res;
	}
	
	@PutMapping(
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  }
	)
	@Operation(
			summary = "Updates a customer", description = "Updates a customer using JSON, XML or YML representation",
			tags = {"Customer"},
			responses = {
				@ApiResponse(description = "Updated", responseCode = "200",
					content = @Content(schema = @Schema(implementation = CustomerResponseVO.class))
				),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public CustomerResponseVO updateCustomer(@RequestBody CustomerRequestVO customer) {
		var res = customerService.updateCustomer(customer);
		return res;
	}	
	
	@DeleteMapping(
			value = "/{id}"
	)
	@Operation(
		summary = "Deletes a customer by ID",
		description = "Deletes a customer by ID",
		tags = {"Customer"},
		responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public ResponseEntity<?> DeleteCustomer(@PathVariable(value = "id") Long id) {
		customerService.deleteCustomer(id);
		return ResponseEntity.noContent().build();
	}
	



}
