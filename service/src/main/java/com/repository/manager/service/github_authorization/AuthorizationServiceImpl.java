package com.repository.manager.service.github_authorization;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import com.repository.manager.core.model.AuthorizationApiResponse;
import com.repository.manager.githubApi.api.GithubAuthApi;

import retrofit2.Call;
import retrofit2.Response;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
	@Value("${CLIENT_ID}")
	private String clientId;

	@Value("${CLIENT_SECRET}")
	private String clientSecret;

	@Value("${STATE}")
	private String state;

	@Autowired
	private GithubAuthApi githubAuthApi;

	@Override
	public RedirectView redirectUser() {
		String url = UriComponentsBuilder.fromUriString("https://github.com/login/oauth/authorize")
				.queryParam("client_id", clientId).queryParam("state", state).queryParam("scope", "user,repo")
				.toUriString();
		return new RedirectView(url);
	}

	@Override
	public AuthorizationApiResponse getUserAccessToken(String code) throws IOException {
		Call<AuthorizationApiResponse> call = githubAuthApi.getUserAccessToken(code, clientId, clientSecret);
		Response<AuthorizationApiResponse> response = call.execute();
		if (response.isSuccessful())
			return response.body();
		throw new IOException("Response Error");
	}

}
