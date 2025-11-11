package com.repository.manager.service.repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.repository.manager.core.model.RepositoryResponse;

public interface RepositoryService {
	List<RepositoryResponse> listRepositories(String authenticationToken)
			throws InterruptedException, ExecutionException;
}
