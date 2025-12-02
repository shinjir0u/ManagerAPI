package com.repository.manager.service.jwt_authorization;

import com.repository.manager.persistence.entity.User;

public interface AuthService {
	String login(String username, String password);

	String addUser(User user);
}
