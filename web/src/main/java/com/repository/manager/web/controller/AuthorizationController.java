package com.repository.manager.web.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.repository.manager.core.model.AuthorizationApiResponse;
import com.repository.manager.service.authorization.AuthorizationService;

import io.github.cdimascio.dotenv.Dotenv;

@RestController
@RequestMapping("/github")
public class AuthorizationController {
	Dotenv dotenv = Dotenv.load();

	@Autowired
	private AuthorizationService authorizationService;

	@GetMapping("/authorize")
	RedirectView redirectUserToAuthorizationPage() {
		return authorizationService.redirectUser();
	}

	@GetMapping("/callback")
	AuthorizationApiResponse getUserAccessTokenToOAuthApp(@RequestParam String code, @RequestParam String state)
			throws IOException {
		if (!state.equals(dotenv.get("STATE")))
			throw new IllegalArgumentException("State did not match.");
		return authorizationService.getUserAccessToken(code);

	}
}
