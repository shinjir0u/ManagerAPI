package com.repository.manager.githubApi.api;

import java.util.List;

import com.repository.manager.core.model.RepositoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface GithubApi {
	@GET("user/repos")
	@Headers({ "Accept: application/vnd.github+json", "X-GitHub-Api-Version: 2022-11-28" })
	public Call<List<RepositoryResponse>> getRepositoriesOfAuthenticatedUser(
			@Header("Authorization") String authorizationHeader);
}
