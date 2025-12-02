package com.repository.manager.service.jwt_authorization;

import com.repository.manager.persistence.entity.Token;
import com.repository.manager.persistence.entity.User;

public interface AuthService {
	Token login(String username, String password);

	String addUser(User user);
}
