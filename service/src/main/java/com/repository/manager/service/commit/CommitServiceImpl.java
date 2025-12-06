package com.repository.manager.service.commit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.repository.manager.core.model.commit.CommitResponse;
import com.repository.manager.github_api.api.GithubApi;
import com.repository.manager.service.current_user.CurrentUserService;

import retrofit2.Call;
import retrofit2.Response;

@Service
public class CommitServiceImpl implements CommitService {
	@Autowired
	private GithubApi githubApi;

	@Autowired
	private CurrentUserService currentUserService;

	@Override
	public List<CommitResponse> getCommits(String owner, String repository, String since, String until, Integer page,
			Integer perPage) throws Exception {
		String authenticationToken = currentUserService.getGithubToken();
		Call<List<CommitResponse>> call = githubApi.getCommitsOfARepo(authenticationToken, owner, repository, since,
				until, page, perPage);
		Response<List<CommitResponse>> response = call.execute();

		if (response.isSuccessful())
			return response.body();
		throw new Exception("No such commit");
	}

	@Override
	public CommitResponse getCommit(String owner, String repo, String ref) throws Exception {
		String authenticationToken = currentUserService.getGithubToken();
		Call<CommitResponse> call = githubApi.getCommit(authenticationToken, owner, repo, ref);
		Response<CommitResponse> response = call.execute();

		if (response.isSuccessful())
			return response.body();
		throw new Exception("No such commit");
	}

}
