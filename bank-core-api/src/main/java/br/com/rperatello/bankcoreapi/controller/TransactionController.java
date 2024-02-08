package br.com.rperatello.bankcoreapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rperatello.bankcoreapi.data.vo.v1.TransactionRequestVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.TransactionResponseVO;
import br.com.rperatello.bankcoreapi.model.MediaType;
import br.com.rperatello.bankcoreapi.services.ITransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/transaction/v1")
@Tag(name = "Transaction", description = "Endpoints for Managing Transactions")
public class TransactionController {	

	
	@Autowired
	private ITransactionService transactionService;		

	@GetMapping(
			value = "/{id}",
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }
	)
	@Operation(
		summary = "Finds a transaction ", description = "Finds a transaction in JSON, XML or YML representation",
		tags = {"Transaction"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200",
				content = @Content(schema = @Schema(implementation = TransactionResponseVO.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public TransactionResponseVO findById(@PathVariable(value = "id") Long id) {
		var res = transactionService.findById(id);
		return res;
	}
	
	
	@PostMapping(
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  }
	)
	@Operation(
			summary = "Adds a new transaction", description = "Adds a new transaction using JSON, XML or YML representation",
			tags = {"Transaction"},
			responses = {
				@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = TransactionRequestVO.class))
				),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public TransactionResponseVO createNewTransaction(@RequestBody TransactionRequestVO transaction) {
		var res = transactionService.createNewTransaction(transaction);
		return res;
	}
	
}
