package br.com.erudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Oauth endpoint")
@RestController
@RequestMapping(value = "oauth-service")
public class OauthController {

	@Autowired
	private UserService service;

	@GetMapping(value = "/search")
	public ResponseEntity<UserDetails> loadUserByUsername(@RequestParam String login) {
		try {
			UserDetails user = service.loadUserByUsername(login);
			return ResponseEntity.ok(user);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}
