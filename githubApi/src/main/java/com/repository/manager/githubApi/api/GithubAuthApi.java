package com.repository.manager.githubApi.api;

import com.repository.manager.core.model.AuthorizationApiResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GithubAuthApi {
	@POST("login/oauth/access_token")
	public Call<AuthorizationApiResponse> getUserAccessToken(@Path("code") String code,
			@Path("client_id") String clientId, @Path("client_secret") String clientSecret);
}
