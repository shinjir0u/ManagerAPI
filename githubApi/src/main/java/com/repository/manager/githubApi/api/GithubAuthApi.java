package com.repository.manager.githubApi.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubAuthApi {
	@GET("login/oauth/authorize")
	public Call<Void> authorizeUser(@Path("client_id") String clientId, @Path("state") String state);
}
