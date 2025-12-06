package com.repository.manager.github_api.api;

import com.repository.manager.core.model.AuthorizationApiResponse;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GithubAuthApi {
	@POST("login/oauth/access_token")
	@Headers({ "Accept: application/json" })
	public Call<AuthorizationApiResponse> getUserAccessToken(@Query("code") String code,
			@Query("client_id") String clientId, @Query("client_secret") String clientSecret);
}
