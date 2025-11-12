package com.repository.manager.service.authorization;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import com.repository.manager.core.model.AuthorizationApiResponse;
import com.repository.manager.githubApi.api.GithubAuthApi;

import io.github.cdimascio.dotenv.Dotenv;
import retrofit2.Call;
import retrofit2.Response;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
	private Dotenv dotenv = Dotenv.load();

	@Autowired
	private GithubAuthApi githubAuthApi;

	@Override
	public RedirectView redirectUser() {
		String url = UriComponentsBuilder.fromUriString("https://github.com/login/oauth/authorize")
				.queryParam("client_id", dotenv.get("CLIENT_ID")).queryParam("state", dotenv.get("STATE"))
				.queryParam("scope", "user,repo").toUriString();
		return new RedirectView(url);
	}

	@Override
	public AuthorizationApiResponse getUserAccessToken(String code) throws IOException {
		Call<AuthorizationApiResponse> call = githubAuthApi.getUserAccessToken(code, dotenv.get("CLIENT_ID"),
				dotenv.get("CLIENT_SECRET"));
		Response<AuthorizationApiResponse> response = call.execute();
		if (response.isSuccessful())
			return response.body();
		throw new IOException("Response Error");
	}

}
