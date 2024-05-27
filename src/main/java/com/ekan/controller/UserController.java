package com.ekan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekan.entity.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuário", description = "API para informações do usuário.")
@RestController
@RequestMapping("/api")
public class UserController {

	@Operation(
			summary = "Detalhes do usuário",
			description = "Exibe todas as informações do usuário com a sua senha criptografada.",
			tags = { "user", "post" })

	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "403", description = "É necessário acesso com autenticação.", content = { @Content(schema = @Schema()) })
	})
	@GetMapping("/user")
	public ResponseEntity<UserDetails> getUserDetails(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return ResponseEntity.ok(userDetails);
	}
}	
