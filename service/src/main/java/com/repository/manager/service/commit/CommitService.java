package com.repository.manager.service.commit;

import java.util.List;

import com.repository.manager.core.model.commit.CommitResponse;

public interface CommitService {
	List<CommitResponse> getCommits(String owner, String repository, Integer page, Integer perPage) throws Exception;
}
