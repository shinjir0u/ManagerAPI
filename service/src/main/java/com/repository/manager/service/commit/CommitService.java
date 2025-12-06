package com.repository.manager.service.commit;

import java.util.List;

import com.repository.manager.core.model.commit.CommitResponse;

public interface CommitService {
	List<CommitResponse> getCommits(String owner, String repository, String since, String until, Integer page,
			Integer perPage) throws Exception;

	CommitResponse getCommit(String owner, String repo, String ref) throws Exception;
}
