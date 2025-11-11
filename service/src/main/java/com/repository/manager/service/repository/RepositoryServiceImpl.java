package com.repository.manager.service.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.repository.manager.core.model.RepositoryResponse;
import com.repository.manager.githubApi.api.GithubApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Service
public class RepositoryServiceImpl implements RepositoryService {
	@Autowired
	private GithubApi githubApi;

	@Override
	public List<RepositoryResponse> listRepositories(String authenticationToken)
			throws InterruptedException, ExecutionException {
		CompletableFuture<List<RepositoryResponse>> completableFuture = new CompletableFuture<>();
		Call<List<RepositoryResponse>> call = githubApi.getRepositoriesOfAuthenticatedUser(authenticationToken);
		call.enqueue(new Callback<List<RepositoryResponse>>() {

			@Override
			public void onResponse(Call<List<RepositoryResponse>> call, Response<List<RepositoryResponse>> response) {
				completableFuture.complete(response.body());
			}

			@Override
			public void onFailure(Call<List<RepositoryResponse>> call, Throwable t) {
				completableFuture.completeExceptionally(t);
			}
		});
		return completableFuture.get();
	}

}
