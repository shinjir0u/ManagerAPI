package com.repository.manager.service.authorization;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import com.repository.manager.core.model.AuthorizationApiResponse;

@Service
public interface AuthorizationService {
	RedirectView redirectUser();

	AuthorizationApiResponse getUserAccessToken(String code) throws IOException;
}
