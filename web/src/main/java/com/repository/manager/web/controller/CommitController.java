package com.repository.manager.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.repository.manager.core.model.commit.CommitResponse;
import com.repository.manager.service.commit.CommitService;
import com.repository.manager.service.jasper.JasperService;

@RestController
@RequestMapping("/{owner}/{repo}/commits")
public class CommitController {
	@Autowired
	private CommitService commitService;

	@Autowired
	private JasperService jasperService;

	@Value("${COMMIT_FILENAME}")
	private String jrxmlFileName;

	@GetMapping
	public ResponseEntity<List<CommitResponse>> getCommits(@PathVariable String owner, @PathVariable String repo,
			@RequestParam(required = false) String since, @RequestParam(required = false) String until,
			@RequestParam(required = false) Integer page,
			@RequestParam(name = "per_page", required = false) Integer perPage) throws Exception {
		List<CommitResponse> commitResponses = commitService.getCommits(owner, repo, since, until, page, perPage);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(commitResponses);
	}

	@GetMapping("/file/{format}")
	public ResponseEntity<Resource> exportCommits(@PathVariable String owner, @PathVariable String repo,
			@RequestParam(required = false) String since, @RequestParam(required = false) String until,
			@PathVariable String format) throws Exception {
		List<CommitResponse> commitResponses = commitService.getCommits(owner, repo, since, until, null, null);
		byte[] exportData = jasperService.exportFile(commitResponses, format, jrxmlFileName);

		ByteArrayResource resource = new ByteArrayResource(exportData);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.contentLength(resource.contentLength())
				.header(HttpHeaders.CONTENT_DISPOSITION,
						ContentDisposition.attachment().filename("commit-report." + format).build().toString())
				.body(resource);
	}

	@GetMapping("/{ref}")
	public ResponseEntity<CommitResponse> getCommit(@PathVariable String owner, @PathVariable String repo,
			@PathVariable String ref) throws Exception {
		CommitResponse commitResponse = commitService.getCommit(owner, repo, ref);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(commitResponse);
	}

	@GetMapping("/{ref}/file/{format}")
	public ResponseEntity<Resource> exportCommit(@PathVariable String owner, @PathVariable String repo,
			@PathVariable String ref, @PathVariable String format) throws Exception {
		CommitResponse commitResponse = commitService.getCommit(owner, repo, ref);
		byte[] exportData = jasperService.exportFile(List.of(commitResponse), format, jrxmlFileName);
		ByteArrayResource resource = new ByteArrayResource(exportData);
		return ResponseEntity.ok().contentLength(resource.contentLength())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION,
						ContentDisposition.attachment().filename("commit-report." + format).build().toString())
				.body(resource);
	}
}
