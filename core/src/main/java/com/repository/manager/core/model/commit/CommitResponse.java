package com.repository.manager.core.model.commit;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitResponse {
	@SerializedName("html_url")
	private String htmlUrl;
	private String sha;
	private Commit commit;
	private File[] files;
}
