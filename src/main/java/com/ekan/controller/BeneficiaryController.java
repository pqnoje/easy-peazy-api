package com.ekan.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ekan.entity.Beneficiary;
import com.ekan.entity.Document;
import com.ekan.repository.BeneficiaryRepository;
import com.ekan.repository.DocumentRepository;
import com.ekan.service.BeneficiaryService;
import com.ekan.service.DocumentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Beneficiário", description = "API para requisições de informações do beneficiário do plano de saúde.")
@RestController
@RequestMapping("/api")
public class BeneficiaryController {
	@Autowired
	BeneficiaryRepository beneficiaryReposisory;
	
	@Autowired
	DocumentRepository documentReposisory;
	
	@Autowired
	BeneficiaryService beneficiaryService;
	
	@Autowired
	DocumentService documentService;
   
	@Operation(
			summary = "Cadastro de beneficiário",
			description = "Cadastra um novo benficiário com os seus documentos.",
			tags = { "beneficiary", "post" })

	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Beneficiary.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "406", description = "Os campos estão incorretos.", content = { @Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = { @Content(schema = @Schema()) })
	})
    @PostMapping(path = "/beneficiary")
	public ResponseEntity<Beneficiary> createBeneficiary(@RequestBody Beneficiary newBeneficiary, HttpServletRequest request) throws ResponseStatusException {
		try {
			Beneficiary beneficiary = this.beneficiaryService.create(newBeneficiary);
			URI location = ServletUriComponentsBuilder.fromRequestUri(request)
					.path("/{id}")
					.buildAndExpand(beneficiary.getId())
					.toUri();
			return ResponseEntity.created(location).body(beneficiary);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
	}
    
	@Operation(
			summary = "Atualização de beneficiário",
			description = "Atualiza os dados cadastrados de um beneficiário.",
			tags = { "beneficiary", "post" })

	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Beneficiary.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "406", description = "Os campos estão incorretos.", content = { @Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = { @Content(schema = @Schema()) })
	})
    @PutMapping(path = "/beneficiary/{id}")
	public ResponseEntity<Beneficiary> updateBeneficiary(@RequestBody Beneficiary updateBeneficiary, @PathVariable Long id, HttpServletRequest request) throws ResponseStatusException {
    	try {
        	Beneficiary beneficiary = this.beneficiaryService.update(updateBeneficiary, id);
        	
			URI location = ServletUriComponentsBuilder.fromRequestUri(request)
				.path("/{id}")
				.buildAndExpand(beneficiary.getId())
				.toUri();
			return ResponseEntity.created(location).body(beneficiary);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    	}
	}
    
	@Operation(
			summary = "Lista todos os beneficiários",
			description = "Atualiza os dados cadastrados de um beneficiário.",
			tags = { "beneficiary", "post" })

	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Beneficiary.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "204", description = "Não existe registro.", content = { @Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = { @Content(schema = @Schema()) })
	})
    @GetMapping(path = "/beneficiary")
    public ResponseEntity<List<Beneficiary>> listBeneficiary() throws ResponseStatusException {
		List<Beneficiary> beneficiaries = this.beneficiaryService.listAll();
		if (!beneficiaries.isEmpty()) {
			return ResponseEntity.ok().body(beneficiaries);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
    
	@Operation(
			summary = "Documentos do beneficiário",
			description = "Atualiza os dados cadastrados de um beneficiário.",
			tags = { "beneficiary", "post" })

	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Document.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "204", description = "Não existe registro.", content = { @Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = { @Content(schema = @Schema()) })
	})
    @GetMapping(path = "/beneficiary/{id}/document")
    public ResponseEntity<List<Document>> listBeneficiaryDocuments(@PathVariable Long id) throws ResponseStatusException {
    	List<Document> documents = this.documentService.listBeneficiaryDocuments(id);
		if (!documents.isEmpty()) {
			return ResponseEntity.ok().body(documents);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
    
	@Operation(
			summary = "Deleta um beneficiário",
			description = "Deleta um beneficiário e todos os seus documentos.",
			tags = { "beneficiary", "post" })

	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Document.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "404", description = "Beneficiário não encontrado.", content = { @Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = { @Content(schema = @Schema()) })
	})
    @DeleteMapping(path = "/beneficiary/{id}")
    public ResponseEntity<String> deleteBeneficiary(@PathVariable Long id) throws ResponseStatusException {
    	try {
    		this.beneficiaryService.delete(id);
        	return ResponseEntity.ok().body("Deleted Successful");
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.notFound().build();
    	}
	}
}


