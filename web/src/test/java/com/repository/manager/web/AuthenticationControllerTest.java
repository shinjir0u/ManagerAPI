package com.repository.manager.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repository.manager.persistence.entity.Token;
import com.repository.manager.service.github_authorization.AuthorizationService;
import com.repository.manager.web.controller.GithubAuthorizationController;

@WebMvcTest(controllers = GithubAuthorizationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AuthorizationService authorizationService;

	@Test
	public void userRedirectTest() throws Exception {
		given(authorizationService.redirectUser()).willReturn(new RedirectView("http://com.example.com"));

		mockMvc.perform(get("/github/authorize")).andExpect(status().isFound());

		verify(authorizationService).redirectUser();
	}

	@Test
	public void getUserAccessTokenTest() throws Exception {
		Token response = Token.builder().value("I'm token").build();

		given(authorizationService.getUserAccessToken(any(String.class))).willReturn(response);

		MvcResult mvcResult = mockMvc.perform(get("/github/callback?code=12345&state=12345"))
				.andExpectAll(status().isOk(), content().contentType(MediaType.APPLICATION_JSON)).andReturn();

		Token apiResponse = convertJsonStringToJson(mvcResult.getResponse().getContentAsString());
		assertThat(apiResponse.getValue()).isEqualTo(response.getValue());

		verify(authorizationService).getUserAccessToken(any(String.class));
	}

	private static Token convertJsonStringToJson(String jsonString)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(jsonString, Token.class);
	}
}
