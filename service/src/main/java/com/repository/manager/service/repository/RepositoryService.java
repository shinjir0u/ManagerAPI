package com.repository.manager.service.repository;

import java.util.List;

import com.repository.manager.core.model.RepositoryResponse;

public interface RepositoryService {
	List<RepositoryResponse> listRepositories(String authenticationToken, int page, int perPage, String sort)
			throws Exception;
}
