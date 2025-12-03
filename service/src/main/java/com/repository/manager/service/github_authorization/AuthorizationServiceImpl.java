package com.repository.manager.service.github_authorization;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import com.repository.manager.core.model.AuthorizationApiResponse;
import com.repository.manager.github_api.api.GithubApi;
import com.repository.manager.github_api.api.GithubAuthApi;
import com.repository.manager.persistence.entity.Token;
import com.repository.manager.persistence.entity.User;
import com.repository.manager.persistence.repository.TokenRepository;
import com.repository.manager.persistence.repository.UserRepository;
import com.repository.manager.service.current_user.CurrentUserService;

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

	@Autowired
	private GithubApi githubApi;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private CurrentUserService currentUserService;

	@Override
	public RedirectView redirectUser() {
		String url = UriComponentsBuilder.fromUriString("https://github.com/login/oauth/authorize")
				.queryParam("client_id", clientId).queryParam("state", state).queryParam("scope", "user,repo")
				.toUriString();
		return new RedirectView(url);
	}

	@Override
	public Token getUserAccessToken(String code) throws IOException {
		String email = currentUserService.getCurrentUser();
		User user = userRepository.findByEmail(email).orElse(null);
		Token generatedToken = null;

		if (user != null) {
			if (user.getGithubToken() != null && validateToken(user.getGithubToken().getValue())) {
				generatedToken = user.getGithubToken();
			} else {
				generatedToken = this.generateNewAccessToken(user, code, clientId, clientSecret);
			}
		}

		return generatedToken;

	}

	private Token generateNewAccessToken(User user, String code, String clientId, String clientSecret)
			throws IOException {
		Call<AuthorizationApiResponse> call = githubAuthApi.getUserAccessToken(code, clientId, clientSecret);
		Response<AuthorizationApiResponse> response = call.execute();
		if (response.isSuccessful()) {
			return updateToken(user, response.body().getAccessToken());
		}
		return null;
	}

	private Boolean validateToken(String tokenValue) throws IOException {
		AuthorizationApiResponse accessToken = new AuthorizationApiResponse(tokenValue, null, null);
		Call<Void> call = githubApi.checkTokenValidity(createBasicAuthorization(), clientId, accessToken);
		Response<Void> response = call.execute();

		return response.isSuccessful();
	}

	private Token updateToken(User user, String tokenValue) {
		Token token = Token.builder().value(tokenValue).build();
		user.setGithubToken(token);
		tokenRepository.save(token);
		userRepository.save(user);
		return token;
	}

	private String createBasicAuthorization() {
		String rawCredentials = clientId + ":" + clientSecret;
		String base64Credentials = Base64.getEncoder().encodeToString(rawCredentials.getBytes());
		return "Basic " + base64Credentials;
	}

}
