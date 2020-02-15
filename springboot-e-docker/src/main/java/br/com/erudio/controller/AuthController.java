package br.com.erudio.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.model.User;
import br.com.erudio.repository.UserRepository;
import br.com.erudio.security.AccountCredentialsVO;
import br.com.erudio.security.JWTUtil;
import br.com.erudio.service.UserService;
import io.swagger.annotations.ApiOperation;


/**
 * Classe responsavel por gerar um novo token, desde que o usuario ainda esteja com seu token valido
 */

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository repository;

	 @ApiOperation(value="Atualiza token")
	@PostMapping("/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		User user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	 
		@ApiOperation(value = "Autentica o usuario por credencial")
		@PostMapping(value = "/login", produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
				"application/json", "application/xml", "application/x-yaml" })
		public ResponseEntity<?> autentica(@RequestBody AccountCredentialsVO data) {
			try {
				var username = data.getUsername();
				var password = data.getPassword();
				
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
				
				var user = repository.findByUsername(username);
				
				var token = "";
				
				if (user != null) {
					token = jwtUtil.generateToken(username);
				}else {
					throw new UsernameNotFoundException("Username " + username + " não encontrado");
				}
				
				Map<Object, Object> model = new HashMap<>();
				model.put("username", username);
				model.put("token", token);
				
				return  ResponseEntity.ok(model);
			} catch (AuthenticationException e) {
				throw new BadCredentialsException("Usuário ou senha invalidos");
			}
			
		}
	


}
