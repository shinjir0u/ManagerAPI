package com.repository.manager.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RepositoryResponse {
	private Long id;
	private String name;
	private String fullname;
	private String url;
	private String description;
}
