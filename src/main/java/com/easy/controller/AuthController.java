package com.easy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easy.entity.User;
import com.easy.repository.UserRepository;
import com.easy.request.AuthRequest;
import com.easy.response.AuthResponse;
import com.easy.security.jwt.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Autenticação", description = "Autentica um usuário cadastrado para emissão de JWT token.")
@RestController
@RequestMapping("/api")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
			JwtUtil jwtUtil, UserRepository userRepository) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
	}

	@Operation(summary = "Autenticação de usuário", description = "Autentica um usuário com suas credenciais e devolve um token JWT que deve ser usado para fazer requisições HTTP de forma segura em endereços protegidos.", tags = {
			"authenticate", "post" })

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "403", description = "As credenciais não estão corretas.", content = {
					@Content(schema = @Schema()) }) })
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) throws Exception {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthResponse(jwt));
	}

	@Operation(summary = "Registro de usuário", description = "Registra um usuário com seu nome de usuário e senha para poder fazer autenticação e poder chamar os endereços que precisam de autenticação.", tags = {
			"register", "post" })

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Nome de usuário já existe.", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "403", description = "As informações passadas estão incorretas.", content = {
					@Content(schema = @Schema()) }) })
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user) {
		if (userRepository.findByUsername(user.getUsername()) != null) {
			return ResponseEntity.badRequest().body("Username already exists");
		}

		user.setPassword(encoder.encode(user.getPassword()));

		userRepository.save(user);
		return ResponseEntity.ok("Registration successful");
	}
}
