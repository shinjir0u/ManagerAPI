package com.repository.manager.githubApi.api;

import java.util.List;

import com.repository.manager.core.model.RepositoryResponse;
import com.repository.manager.core.model.commit.CommitResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubApi {
	@GET("user/repos")
	@Headers({ "Accept: application/vnd.github+json", "X-GitHub-Api-Version: 2022-11-28" })
	public Call<List<RepositoryResponse>> getRepositoriesOfAuthenticatedUser(
			@Header("Authorization") String authorizationHeader, @Query("page") Integer page,
			@Query("per_page") Integer perPage, @Query("sort") String sort);

	@GET("repos/{owner}/{repo}/commits")
	@Headers({ "Accept: application/vnd.github+json" })
	public Call<List<CommitResponse>> getCommitsOfARepo(@Path("owner") String owner, @Path("repo") String repo,
			@Query("page") Integer page, @Query("per_page") Integer perPage);

	@GET("repos/{owner}/{repo}/commits/{ref}")
	@Headers({ "Accept: application/vnd.github+json" })
	public Call<CommitResponse> getCommit(String owner, String repo, String ref, Integer page, Integer perPage);
}
