package com.repository.manager.service.github_authorization;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import com.repository.manager.persistence.entity.Token;

@Service
public interface AuthorizationService {
	RedirectView redirectUser();

	Token getUserAccessToken(String code) throws IOException;
}
