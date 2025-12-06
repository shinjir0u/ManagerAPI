package com.repository.manager.service.current_user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.repository.manager.persistence.entity.User;
import com.repository.manager.persistence.repository.UserRepository;

@Service
public class CurrentUserService {
	@Autowired
	private UserRepository userRepository;

	public String getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken))
			return authentication.getName();
		return null;
	}

	public String getGithubToken() {
		String email = getCurrentUser();
		User user = userRepository.findByEmail(email).orElse(null);
		return "Bearer " + user.getGithubToken().getValue();
	}
}
