package com.repository.manager.github_api.api;

import java.util.List;

import com.repository.manager.core.model.AuthorizationApiResponse;
import com.repository.manager.core.model.RepositoryResponse;
import com.repository.manager.core.model.commit.CommitResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubApi {
	@POST("/applications/{client_id}/token")
	@Headers({ "Accept: application/vnd.github+json", "X-GitHub-Api-Version: 2022-11-28" })
	public Call<Void> checkTokenValidity(@Header("Authorization") String credentials,
			@Path("client_id") String clientId, @Body AuthorizationApiResponse authorizationApiResponse);

	@GET("user/repos")
	@Headers({ "Accept: application/vnd.github+json", "X-GitHub-Api-Version: 2022-11-28" })
	public Call<List<RepositoryResponse>> getRepositoriesOfAuthenticatedUser(
			@Header("Authorization") String authorizationHeader, @Query("page") Integer page,
			@Query("per_page") Integer perPage, @Query("sort") String sort);

	@GET("repos/{owner}/{repo}/commits")
	@Headers({ "Accept: application/vnd.github+json" })
	public Call<List<CommitResponse>> getCommitsOfARepo(@Header("Authorization") String authorizationHeader,
			@Path("owner") String owner, @Path("repo") String repo, @Query("since") String since,
			@Query("until") String until, @Query("page") Integer page, @Query("per_page") Integer perPage);

	@GET("repos/{owner}/{repo}/commits/{ref}")
	@Headers({ "Accept: application/vnd.github+json" })
	public Call<CommitResponse> getCommit(@Header("Authorization") String authorizationHeader,
			@Path("owner") String owner, @Path("repo") String repo, @Path("ref") String ref);
}
