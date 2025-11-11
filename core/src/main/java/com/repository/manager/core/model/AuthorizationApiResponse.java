package com.repository.manager.core.model;

import lombok.Data;

@Data
public class AuthorizationApiResponse {
	private String accessToken;
	private String scope;
	private String tokenType;
}
