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

import br.com.rperatello.bankcoreapi.data.vo.v1.AgencyRequestVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.AgencyResponseVO;
import br.com.rperatello.bankcoreapi.model.MediaType;
import br.com.rperatello.bankcoreapi.services.IAgencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/agency/v1")
@Tag(name = "Agency", description = "Endpoints for Managing Agencies")
public class AgencyController {	

	
	@Autowired
	private IAgencyService agencyService;
	
	@GetMapping(
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }
	)
	@Operation(
		summary = "Get all agencies", description = "Finds all agencies in JSON, XML or YML representation",
		tags = {"Agency"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200",
				content = {
					@Content(
						mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = AgencyResponseVO.class))
					)
			}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public List<AgencyResponseVO> getAll() {
		var res = agencyService.getAll();
		return res;
	}

	@GetMapping(
			value = "/{id}",
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }
	)
	@Operation(
		summary = "Finds a agency", description = "Finds a agency in JSON, XML or YML representation",
		tags = {"Agency"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200",
				content = @Content(schema = @Schema(implementation = AgencyResponseVO.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public AgencyResponseVO findById(@PathVariable(value = "id") Long id) {
		var res = agencyService.findById(id);
		return res;
	}
	
	@PostMapping(
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  }
	)
	@Operation(
			summary = "Adds a new agency", description = "Adds a new agency using JSON, XML or YML representation",
			tags = {"Agency"},
			responses = {
				@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = AgencyResponseVO.class))
				),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public AgencyResponseVO createNewAgency(@RequestBody AgencyRequestVO agency) {
		var res = agencyService.createNewAgency(agency);
		return res;
	}
	
	@PutMapping(
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  }
	)
	@Operation(
			summary = "Updates a agency", description = "Updates a agency using JSON, XML or YML representation",
			tags = {"Agency"},
			responses = {
				@ApiResponse(description = "Updated", responseCode = "200",
					content = @Content(schema = @Schema(implementation = AgencyResponseVO.class))
				),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public AgencyResponseVO updateAgency(@RequestBody AgencyRequestVO agency) {
		var res = agencyService.updateAgency(agency);
		return res;
	}	
	
	@DeleteMapping(
			value = "/{id}"
	)
	@Operation(
		summary = "Deletes a agency by ID",
		description = "Deletes a agency by ID",
		tags = {"Agency"},
		responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public ResponseEntity<?> DeleteAgency(@PathVariable(value = "id") Long id) {
		agencyService.deleteAgency(id);
		return ResponseEntity.noContent().build();
	}
	



}
