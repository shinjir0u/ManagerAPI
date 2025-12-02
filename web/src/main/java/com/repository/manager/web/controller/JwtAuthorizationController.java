package com.repository.manager.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.repository.manager.persistence.entity.Token;
import com.repository.manager.persistence.entity.User;
import com.repository.manager.service.jwt_authorization.AuthService;

@RestController
public class JwtAuthorizationController {
	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public String registerHandler(@RequestBody User user) {
		authService.addUser(user);
		return "User added successfully";
	}

	@PostMapping("/login")
	public ResponseEntity<Token> loginHandler(@RequestBody User user) {
		Token token = authService.login(user.getEmail(), user.getPassword());
		return ResponseEntity.ok(token);
	}

	@GetMapping("/authorizer")
	public String auth() {
		return "You are authorized";
	}
}
