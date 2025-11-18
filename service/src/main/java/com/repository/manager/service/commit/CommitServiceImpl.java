package com.repository.manager.service.commit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.repository.manager.core.model.commit.CommitResponse;
import com.repository.manager.githubApi.api.GithubApi;
import com.repository.manager.service.repository.RepositoryServiceImpl;

import retrofit2.Call;
import retrofit2.Response;

@Service
public class CommitServiceImpl implements CommitService {

	private final RepositoryServiceImpl repositoryServiceImpl;
	@Autowired
	private GithubApi githubApi;

	CommitServiceImpl(RepositoryServiceImpl repositoryServiceImpl) {
		this.repositoryServiceImpl = repositoryServiceImpl;
	}

	@Override
	public List<CommitResponse> getCommits(String owner, String repository, Integer page, Integer perPage)
			throws Exception {
		Call<List<CommitResponse>> call = githubApi.getCommitsOfARepo(owner, repository, page, perPage);
		Response<List<CommitResponse>> response = call.execute();

		if (response.isSuccessful())
			return response.body();
		throw new Exception("No such commit");
	}

	@Override
	public CommitResponse getCommit(String owner, String repo, String ref, Integer page, Integer perPage)
			throws Exception {
		Call<CommitResponse> call = githubApi.getCommit(owner, repo, ref, page, perPage);
		Response<CommitResponse> response = call.execute();

		if (response.isSuccessful())
			return response.body();
		throw new Exception("No such commit");
	}

}
