package com.repository.manager.core.model.commit;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {
	private String filename;
	private Integer additions;
	private Integer deletions;
	private Integer changes;
	private String status;
	@SerializedName("raw_url")
	private String rawUrl;
	@SerializedName("blob_url")
	private String blobUrl;
}