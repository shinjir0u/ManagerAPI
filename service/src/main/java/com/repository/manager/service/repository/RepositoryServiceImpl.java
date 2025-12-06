package com.repository.manager.service.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.repository.manager.core.model.RepositoryResponse;
import com.repository.manager.github_api.api.GithubApi;
import com.repository.manager.service.current_user.CurrentUserService;

import retrofit2.Call;
import retrofit2.Response;

@Service
public class RepositoryServiceImpl implements RepositoryService {
	@Autowired
	private GithubApi githubApi;

	@Autowired
	private CurrentUserService currentUserService;

	@Override
	public List<RepositoryResponse> listRepositories(Integer page, Integer perPage, String sort) throws Exception {
		String authenticationToken = currentUserService.getGithubToken();
		Call<List<RepositoryResponse>> call = githubApi.getRepositoriesOfAuthenticatedUser(authenticationToken, page,
				perPage, sort);
		Response<List<RepositoryResponse>> response = call.execute();

		if (response.isSuccessful())
			return response.body();
		throw new Exception("Repo Access Error");
	}
}
