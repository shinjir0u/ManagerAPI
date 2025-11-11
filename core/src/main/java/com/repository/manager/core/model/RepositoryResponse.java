package com.repository.manager.core.model;

import lombok.Data;

@Data
public class RepositoryResponse {
	private Long id;
	private String name;
	private String fullname;
	private String url;
	private String description;
}
