package com.repository.manager.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.repository.manager.core.model.commit.CommitResponse;
import com.repository.manager.service.commit.CommitService;

@RestController
@RequestMapping("/{owner}/{repo}/commits")
public class CommitController {
	@Autowired
	private CommitService commitService;

	@GetMapping
	public ResponseEntity<List<CommitResponse>> getCommits(@PathVariable String owner, @PathVariable String repo,
			@RequestParam(required = false) String since, @RequestParam(required = false) String until,
			@RequestParam(required = false) Integer page,
			@RequestParam(name = "per_page", required = false) Integer perPage) throws Exception {
		List<CommitResponse> commitResponses = commitService.getCommits(owner, repo, since, until, page, perPage);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(commitResponses);
	}

	@GetMapping("/{ref}")
	public ResponseEntity<CommitResponse> getCommit(@PathVariable String owner, @PathVariable String repo,
			@PathVariable String ref, @RequestParam(required = false) Integer page,
			@RequestParam(name = "per_page", required = false) Integer perPage) throws Exception {
		CommitResponse commitResponse = commitService.getCommit(owner, repo, ref, page, perPage);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(commitResponse);
	}
}
