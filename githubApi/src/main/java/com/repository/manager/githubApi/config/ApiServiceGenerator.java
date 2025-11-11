package com.repository.manager.githubApi.config;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceGenerator {
	public static OkHttpClient client =
			new OkHttpClient().newBuilder().build();
	
	public static Retrofit createRetrofit(String baseUrl) {
		return new Retrofit.Builder()
				.baseUrl(baseUrl)
				.addConverterFactory(GsonConverterFactory.create())
				.client(client)
				.build();
	}
}
