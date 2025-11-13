package com.repository.manager.service.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.repository.manager.core.model.RepositoryResponse;
import com.repository.manager.githubApi.api.GithubApi;

import retrofit2.Call;
import retrofit2.Response;

@Service
public class RepositoryServiceImpl implements RepositoryService {
	@Autowired
	private GithubApi githubApi;

	@Override
	public List<RepositoryResponse> listRepositories(String authenticationToken, int page, int perPage, String sort)
			throws Exception {
		Call<List<RepositoryResponse>> call = githubApi.getRepositoriesOfAuthenticatedUser(authenticationToken, page,
				perPage, sort);
		Response<List<RepositoryResponse>> response = call.execute();

		if (response.isSuccessful())
			return response.body();
		throw new Exception("Repo Access Error");
	}
}
