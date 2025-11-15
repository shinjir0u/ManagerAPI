package com.repository.manager.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class RepositoryResponse {
	@Id
	private Long id;
	private String name;
	private String fullname;
	private String url;
	private String description;
}
