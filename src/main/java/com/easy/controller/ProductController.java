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

import com.easy.entity.Product;
import com.easy.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Produto", description = "API para requisições de informações do produto.")
@RestController
@RequestMapping("/api")
public class ProductController {
	@Autowired
	ProductService productService;

	@Operation(summary = "Cadastro de produto", description = "Cadastra um novo produto com sua prateleira.", tags = {
			"product", "post" })

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "406", description = "Os campos estão incorretos.", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = {
					@Content(schema = @Schema()) }) })
	@PostMapping(path = "/product")
	public ResponseEntity<Product> createProduct(@RequestBody Product newProduct, HttpServletRequest request)
			throws ResponseStatusException {
		try {
			Product product = this.productService.create(newProduct);
			URI location = ServletUriComponentsBuilder.fromRequestUri(request).path("/{id}")
					.buildAndExpand(product.getId()).toUri();
			return ResponseEntity.created(location).body(product);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
	}

	@Operation(summary = "Atualização de produto", description = "Atualiza os dados cadastrados de um produto.", tags = {
			"product", "post" })

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "406", description = "Os campos estão incorretos.", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = {
					@Content(schema = @Schema()) }) })
	@PutMapping(path = "/product/{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product updateProduct, @PathVariable Long id,
			HttpServletRequest request) throws ResponseStatusException {
		try {
			Product product = this.productService.update(updateProduct, id);

			URI location = ServletUriComponentsBuilder.fromRequestUri(request).path("/{id}")
					.buildAndExpand(product.getId()).toUri();
			return ResponseEntity.created(location).body(product);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
	}

	@Operation(summary = "Informações do produto", description = "Retorna um produto com suas informações.", tags = {
			"shelf", "post" })

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Não existe o registro.", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = {
					@Content(schema = @Schema()) }) })
	@GetMapping(path = "/product/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id, HttpServletRequest request)
			throws ResponseStatusException {
		try {
			Product product = this.productService.getById(id);

			URI location = ServletUriComponentsBuilder.fromRequestUri(request).path("/{id}")
					.buildAndExpand(product.getId()).toUri();
			return ResponseEntity.created(location).body(product);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Operation(summary = "Lista todos os produtos", description = "Lista todos os produtos.", tags = { "product",
			"post" })

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "204", description = "Não existe registro.", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = {
					@Content(schema = @Schema()) }) })
	@GetMapping(path = "/product")
	public ResponseEntity<List<Product>> listBeneficiary() throws ResponseStatusException {
		List<Product> products = this.productService.listAll();
		if (!products.isEmpty()) {
			return ResponseEntity.ok().body(products);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@Operation(summary = "Deleta um produto", description = "Deleta um produto.", tags = { "product", "post" })

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Beneficiário não encontrado.", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = {
					@Content(schema = @Schema()) }) })
	@DeleteMapping(path = "/product/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws ResponseStatusException {
		try {
			this.productService.delete(id);
			return ResponseEntity.ok().body("Deleted Successful");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Produtos por prateleira", description = "Lista todos os produtos cadastrados para uma prateleira.", tags = {
			"product", "post" })

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "204", description = "Não existe registro.", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = {
					@Content(schema = @Schema()) }) })
	@GetMapping(path = "/product/shelf/{id}")
	public ResponseEntity<List<Product>> listProductByShelf(@PathVariable Long id) throws ResponseStatusException {
		List<Product> products = this.productService.listAllByShelf(id);
		if (!products.isEmpty()) {
			return ResponseEntity.ok().body(products);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@Operation(summary = "Produtos por cesta de produtos", description = "Lista todos os produtos cadastrados para uma cesta de produtos.", tags = {
			"product", "post" })

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "204", description = "Não existe registro.", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", description = "É necesssário autenticação para esta requisição.", content = {
					@Content(schema = @Schema()) }) })
	@GetMapping(path = "/product/basket/{id}")
	public ResponseEntity<List<Product>> listProductByBasket(@PathVariable Long id) throws ResponseStatusException {
		List<Product> products = this.productService.listAllByBasket(id);
		if (!products.isEmpty()) {
			return ResponseEntity.ok().body(products);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
}
