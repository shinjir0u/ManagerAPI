package com.repository.manager.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.repository.manager.core.model.commit.CommitResponse;
import com.repository.manager.service.commit.CommitService;

@RestController
public class CommitController {
	@Autowired
	private CommitService commitService;

	@GetMapping("/{owner}/{repo}/commits")
	public ResponseEntity<List<CommitResponse>> getCommits(@PathVariable String owner, @PathVariable String repo,
			@RequestParam(required = false) Integer page,
			@RequestParam(name = "per_page", required = false) Integer perPage) throws Exception {
		List<CommitResponse> commitResponses = commitService.getCommits(owner, repo, page, perPage);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(commitResponses);
	}
}
