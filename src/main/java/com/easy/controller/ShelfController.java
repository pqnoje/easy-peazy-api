package com.easy.controller;

import java.net.URI;
import java.util.List;

import javax.swing.text.Document;

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

import com.easy.entity.Shelf;
import com.easy.service.ShelfService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Prateleira", description = "API para requisições de informações da prateleira.")
@RestController
@RequestMapping("/api")
public class ShelfController {
	@Autowired
	ShelfService shelfService;

	@Operation(summary = "Cadastro de prateleira", description = "Cadastra uma nova prateleira.", tags = { "shelf",
			"post" })

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Shelf.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "406", description = "Os campos estão incorretos.", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = {
					@Content(schema = @Schema()) }) })
	@PostMapping(path = "/shelf")
	public ResponseEntity<Shelf> createShelf(@RequestBody Shelf newShelf, HttpServletRequest request)
			throws ResponseStatusException {
		try {
			Shelf shelf = this.shelfService.create(newShelf);
			URI location = ServletUriComponentsBuilder.fromRequestUri(request).path("/{id}")
					.buildAndExpand(shelf.getId()).toUri();
			return ResponseEntity.created(location).body(shelf);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
	}

	@Operation(summary = "Atualização de prateleira", description = "Atualiza os dados cadastrados de uma prateleira.", tags = {
			"shelf", "post" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Shelf.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "406", description = "Os campos estão incorretos.", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = {
					@Content(schema = @Schema()) }) })
	@PutMapping(path = "/shelf/{id}")
	public ResponseEntity<Shelf> updateShelf(@RequestBody Shelf updateShelf, @PathVariable Long id,
			HttpServletRequest request) throws ResponseStatusException {
		try {
			Shelf shelf = this.shelfService.update(updateShelf, id);

			URI location = ServletUriComponentsBuilder.fromRequestUri(request).path("/{id}")
					.buildAndExpand(shelf.getId()).toUri();
			return ResponseEntity.created(location).body(shelf);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
	}

	@Operation(summary = "Informações de prateleira", description = "Retorna uma prateleira com suas informações.", tags = {
			"shelf", "get" })

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Shelf.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Não existe o registro.", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = {
					@Content(schema = @Schema()) }) })
	@GetMapping(path = "/shelf/{id}")
	public ResponseEntity<Shelf> getShelfById(@PathVariable Long id, HttpServletRequest request)
			throws ResponseStatusException {
		try {
			Shelf shelf = this.shelfService.getById(id);

			URI location = ServletUriComponentsBuilder.fromRequestUri(request).path("/{id}")
					.buildAndExpand(shelf.getId()).toUri();
			return ResponseEntity.created(location).body(shelf);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Operation(summary = "Lista todos as prateleiras", description = "Lista todos as prateleiras.", tags = { "shelf",
			"post" })

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Shelf.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "204", description = "Não existe registro.", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = {
					@Content(schema = @Schema()) }) })
	@GetMapping(path = "/shelf")
	public ResponseEntity<List<Shelf>> listShelfs() throws ResponseStatusException {
		List<Shelf> shelfs = this.shelfService.listAll();
		if (!shelfs.isEmpty()) {
			return ResponseEntity.ok().body(shelfs);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@Operation(summary = "Deleta uma prateleira", description = "Deleta um produto.", tags = { "shelf", "post" })

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Document.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Beneficiário não encontrado.", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = {
					@Content(schema = @Schema()) }) })
	@DeleteMapping(path = "/shelf/{id}")
	public ResponseEntity<String> deleteShelf(@PathVariable Long id) throws ResponseStatusException {
		try {
			this.shelfService.delete(id);
			return ResponseEntity.ok().body("Deleted Successful");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

}
