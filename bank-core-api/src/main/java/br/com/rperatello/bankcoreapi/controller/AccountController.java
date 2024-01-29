package br.com.rperatello.bankcoreapi.controller;

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

import br.com.rperatello.bankcoreapi.data.vo.v1.AccountStatusRequestVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.AccountResponseVO;
import br.com.rperatello.bankcoreapi.model.MediaType;
import br.com.rperatello.bankcoreapi.services.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/account/v1")
@Tag(name = "Account", description = "Endpoints for Managing Accounts")
public class AccountController {	

	
	@Autowired
	private IAccountService accountService;
	
	@GetMapping(
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }
	)
	@Operation(
		summary = "Get all accounts", description = "Finds all accounts in JSON, XML or YML representation",
		tags = {"Account"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200",
				content = {
					@Content(
						mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = AccountResponseVO.class))
					)
			}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public List<AccountResponseVO> getAll() {
		var res = accountService.getAll();
		return res;
	}

	@GetMapping(
			value = "/{id}",
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }
	)
	@Operation(
		summary = "Finds a account ", description = "Finds a account in JSON, XML or YML representation",
		tags = {"Account"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200",
				content = @Content(schema = @Schema(implementation = AccountResponseVO.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public AccountResponseVO findById(@PathVariable(value = "id") Long id) {
		var res = accountService.findById(id);
		return res;
	}
	
	@PostMapping(
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  }
	)
	@Operation(
			summary = "Adds a new account", description = "Adds a new account using JSON, XML or YML representation",
			tags = {"Account"},
			responses = {
				@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = AccountResponseVO.class))
				),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public AccountResponseVO createNewAccount(@RequestBody AccountStatusRequestVO account) {
		var res = accountService.createNewAccount(account);
		return res;
	}
	
	@PutMapping(
			value = "status",
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  }
	)
	@Operation(
			summary = "Updates account status", description = "Updates a account status using JSON, XML or YML representation",
			tags = {"Account"},
			responses = {
				@ApiResponse(description = "Updated", responseCode = "200",
					content = @Content(schema = @Schema(implementation = AccountResponseVO.class))
				),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public AccountResponseVO updateAccountStatus(@RequestBody AccountStatusRequestVO account) {
		var res = accountService.updateAccountStatus(account);
		return res;
	}	
	
	@DeleteMapping(
			value = "/{id}"
	)
	@Operation(
		summary = "Deletes a account by ID",
		description = "Deletes a account by ID",
		tags = {"Account"},
		responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public ResponseEntity<?> DeleteAccount(@PathVariable(value = "id") Long id) {
		accountService.deleteAccount(id);
		return ResponseEntity.noContent().build();
	}	
	
}
