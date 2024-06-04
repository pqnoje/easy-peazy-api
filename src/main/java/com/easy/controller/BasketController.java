package com.easy.controller;

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

import com.easy.entity.Basket;
import com.easy.service.BasketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Cesta de Compras", description = "API para requisições de informações da cesta de compras.")
@RestController
@RequestMapping("/api")
public class BasketController {
	@Autowired
	BasketService basketService;

	@Operation(summary = "Cadastro de cesta de compras", description = "Cadastra uma nova cesta de compras.", tags = {
			"basket", "post" })

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Basket.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "406", description = "Os campos estão incorretos.", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = {
					@Content(schema = @Schema()) }) })
	@PostMapping(path = "/basket")
	public ResponseEntity<Basket> createBasket(@RequestBody Basket newBasket, HttpServletRequest request)
			throws ResponseStatusException {
		try {
			Basket basket = this.basketService.create(newBasket);
			URI location = ServletUriComponentsBuilder.fromRequestUri(request).path("/{id}")
					.buildAndExpand(basket.getId()).toUri();
			return ResponseEntity.created(location).body(basket);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
	}

	@Operation(summary = "Atualização de cesta de comrpas", description = "Atualiza os dados cadastrados de uma cesta de comrpas.", tags = {
			"basket", "post" })

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Basket.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "406", description = "Os campos estão incorretos.", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = {
					@Content(schema = @Schema()) }) })
	@PutMapping(path = "/basket/{id}")
	public ResponseEntity<Basket> updateBasket(@RequestBody Basket updateBasket, @PathVariable Long id,
			HttpServletRequest request) throws ResponseStatusException {
		try {
			Basket basket = this.basketService.update(updateBasket, id);

			URI location = ServletUriComponentsBuilder.fromRequestUri(request).path("/{id}")
					.buildAndExpand(basket.getId()).toUri();
			return ResponseEntity.created(location).body(basket);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
	}

	@Operation(summary = "Informações da cesta de compras", description = "Retorna uma cesta de compras com suas informações.", tags = {
			"shelf", "post" })

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Basket.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Não existe o registro.", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = {
					@Content(schema = @Schema()) }) })
	@GetMapping(path = "/basket/{id}")
	public ResponseEntity<Basket> getBasketById(@PathVariable Long id, HttpServletRequest request)
			throws ResponseStatusException {
		try {
			Basket basket = this.basketService.getById(id);

			URI location = ServletUriComponentsBuilder.fromRequestUri(request).path("/{id}")
					.buildAndExpand(basket.getId()).toUri();
			return ResponseEntity.created(location).body(basket);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Operation(summary = "Lista todos as cestas de compras", description = "Lista todos as cestas de compra.", tags = {
			"basket", "post" })

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Basket.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "204", description = "Não existe registro.", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = {
					@Content(schema = @Schema()) }) })
	@GetMapping(path = "/basket")
	public ResponseEntity<List<Basket>> listBaskets() throws ResponseStatusException {
		List<Basket> baskets = this.basketService.listAll();
		if (!baskets.isEmpty()) {
			return ResponseEntity.ok().body(baskets);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@Operation(summary = "Deleta uma cesta de compras", description = "Deleta uma cesta de compras.", tags = { "basket",
			"post" })

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Basket.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Beneficiário não encontrado.", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = {
					@Content(schema = @Schema()) }) })
	@DeleteMapping(path = "/basket/{id}")
	public ResponseEntity<String> deleteBasket(@PathVariable Long id) throws ResponseStatusException {
		try {
			this.basketService.delete(id);
			return ResponseEntity.ok().body("Deleted Successful");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

}
