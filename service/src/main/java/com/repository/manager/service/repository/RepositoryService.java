package com.repository.manager.service.repository;

import java.util.List;

import com.repository.manager.core.model.RepositoryResponse;

public interface RepositoryService {
	List<RepositoryResponse> listRepositories(Integer page, Integer perPage, String sort) throws Exception;
}
