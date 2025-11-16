package com.repository.manager.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.repository.manager.core.model.RepositoryResponse;
import com.repository.manager.service.jasper.JasperService;
import com.repository.manager.service.repository.RepositoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;

@RestController
@RequestMapping("/repositories")
@SecurityRequirement(name = "GithubAuthToken")
@SecurityScheme(name = "RepositorySecurity", in = SecuritySchemeIn.HEADER, type = SecuritySchemeType.APIKEY, bearerFormat = "Bearer <Your API KEY>", description = "Authorize your account first to perform operations.")
@Tag(name = "Repository", description = "Perform operations on repositories")
public class RepositoryController {
	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private JasperService jasperService;

	@GetMapping
	@Operation(summary = "List repositories", description = "Get repositories of the authenticaed user")
	@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RepositoryResponse.class)))
	public List<RepositoryResponse> getRepositories(@RequestHeader("Authorization") String authorizationToken,
			@Nullable @RequestParam Integer page, @Nullable @RequestParam("per_page") Integer perPage,
			@Nullable @RequestParam String sort) throws Exception {
		return repositoryService.listRepositories(authorizationToken, page, perPage, sort);
	}

	@GetMapping("/{format}")
	@Operation(summary = "Export the repositories", description = "Export the repositories data in desired format. Available formats are pdf, html, docx and xlsx.")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Void.class)))
	public ResponseEntity<Resource> exportRepositoryReport(@RequestHeader("Authorization") String authorizationToken,
			@PathVariable String format) throws Exception {
		byte[] exportData = jasperService.exportFile(authorizationToken, format);
		ByteArrayResource resource = new ByteArrayResource(exportData);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.contentLength(resource.contentLength())
				.header(HttpHeaders.CONTENT_DISPOSITION,
						ContentDisposition.attachment().filename("repository-report." + format).build().toString())
				.body(resource);

	}
}
