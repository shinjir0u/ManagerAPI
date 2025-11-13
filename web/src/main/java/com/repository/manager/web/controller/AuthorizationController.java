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
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/github")
@Tag(name = "Authorization", description = "Authorize the App to Github Account")
@SecurityRequirement(name = "AppAuthToken")
public class AuthorizationController {
	Dotenv dotenv = Dotenv.load();

	@Autowired
	private AuthorizationService authorizationService;

	@Operation(summary = "Redirects to Github Page to authorize the API")
	@ApiResponse(responseCode = "200", description = "This endpoint calls the Github API for authoriaztion "
			+ ", after which Github API will return the access token.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorizationApiResponse.class)))
	@GetMapping("/authorize")
	RedirectView redirectUserToAuthorizationPage() {
		return authorizationService.redirectUser();
	}

	@Hidden
	@GetMapping("/callback")
	AuthorizationApiResponse getUserAccessTokenToOAuthApp(@RequestParam String code, @RequestParam String state)
			throws IOException {
		if (!state.equals(dotenv.get("STATE")))
			throw new IllegalArgumentException("State did not match.");
		return authorizationService.getUserAccessToken(code);

	}
}
