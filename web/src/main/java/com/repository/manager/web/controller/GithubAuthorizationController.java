package com.repository.manager.web.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.repository.manager.core.model.AuthorizationApiResponse;
import com.repository.manager.persistence.entity.Token;
import com.repository.manager.service.github_authorization.AuthorizationService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/github")
@Tag(name = "Authorization", description = "Authorize the App to Github Account")
@SecurityScheme(name = "AuthorizationSecurity", in = SecuritySchemeIn.HEADER, type = SecuritySchemeType.APIKEY, bearerFormat = "Bearer <Your API KEY>", description = "Authorize the app to connect to your github account.")
@SecurityRequirement(name = "AppAuthToken")
public class GithubAuthorizationController {
	@Value("${STATE}")
	private String state;

	@Autowired
	private AuthorizationService authorizationService;

	@Operation(summary = "Redirects to Github Page to authorize the API", description = "This endpoint calls the Github API for authoriaztion "
			+ ", after which Github API will return the access token.")
	@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorizationApiResponse.class)))
	@GetMapping("/authorize")
	RedirectView redirectUserToAuthorizationPage() {
		return authorizationService.redirectUser();
	}

	@Hidden
	@GetMapping("/login")
	ResponseEntity<Token> getUserAccessTokenToOAuthApp(@RequestParam String code, @RequestParam String state)
			throws IOException {
		if (!state.equals(state))
			throw new IllegalArgumentException("State did not match.");
		Token response = authorizationService.getUserAccessToken(code);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}
}
