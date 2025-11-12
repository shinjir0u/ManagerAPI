package com.repository.manager.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorizationApiResponse {
	private String accessToken;
	private String scope;
	private String tokenType;
}
