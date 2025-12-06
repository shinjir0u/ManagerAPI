package com.repository.manager.core.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorizationApiResponse {
	@SerializedName("access_token")
	private String accessToken;
	private String scope;
	@SerializedName("token_type")
	private String tokenType;
}
