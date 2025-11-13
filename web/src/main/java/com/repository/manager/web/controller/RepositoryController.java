package com.repository.manager.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.repository.manager.core.model.RepositoryResponse;
import com.repository.manager.service.repository.RepositoryService;

@RestController
@RequestMapping("/repositories")
public class RepositoryController {
	@Autowired
	private RepositoryService repositoryService;

	@GetMapping
	public List<RepositoryResponse> getRepositories(@RequestHeader("Authorization") String authorizationToken)
			throws Exception {
		return repositoryService.listRepositories(authorizationToken);
	}

}
