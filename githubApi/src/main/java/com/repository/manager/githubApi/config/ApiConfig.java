package com.repository.manager.githubApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.repository.manager.githubApi.api.GithubApi;
import com.repository.manager.githubApi.api.GithubAuthApi;

import retrofit2.Retrofit;

@Configuration
public class ApiConfig {
	@Bean
	Retrofit authRetrofit() {
		return ApiServiceGenerator.createRetrofit("https://github.com");
	}
	
	@Bean
	GithubAuthApi githubAuthApi(Retrofit authRetrofit) {
		return authRetrofit.create(GithubAuthApi.class);
	}
	
	@Bean
	Retrofit retrofit() {
		return ApiServiceGenerator.createRetrofit("https://api.github.com");
	}
	
	@Bean
	GithubApi githubApi(Retrofit retrofit) {
		return retrofit.create(GithubApi.class);
	}
	
	
}
