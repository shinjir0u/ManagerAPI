package com.repository.manager.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repository.manager.core.model.RepositoryResponse;
import com.repository.manager.service.repository.RepositoryService;
import com.repository.manager.web.controller.RepositoryController;

@WebMvcTest(controllers = RepositoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RepositoryControllerTest {
	@MockitoBean
	private RepositoryService repositoryService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getRepositoriesTest() throws Exception {
		RepositoryResponse repositoryResponse1 = RepositoryResponse.builder().id(1L).name("Repo1")
				.fullname("user/Repo1").url("google.com").description("This is repo 1").build();
		RepositoryResponse repositoryResponse2 = RepositoryResponse.builder().id(2L).name("Repo2")
				.fullname("user/Repo2").url("google.com").description("This is repo 2").build();
		List<RepositoryResponse> repositoryResponses = new ArrayList<>(
				List.of(repositoryResponse1, repositoryResponse2));
		given(repositoryService.listRepositories(any(Integer.class), any(Integer.class), anyString()))
				.willReturn(repositoryResponses);

		MvcResult mvcResult = mockMvc
				.perform(get("/repositories?page=3&per_page=2&sort=created").header("Authorization", "12345"))
				.andExpectAll(status().isOk(), content().contentType(MediaType.APPLICATION_JSON)).andReturn();

		List<RepositoryResponse> responses = convertJsonStringToJson(mvcResult.getResponse().getContentAsString());
		assertThat(responses.size()).isEqualTo(2);

		verify(repositoryService).listRepositories(any(Integer.class), any(Integer.class), anyString());
	}

	private static List<RepositoryResponse> convertJsonStringToJson(String jsonString)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(jsonString, new TypeReference<List<RepositoryResponse>>() {
		});
	}
}
