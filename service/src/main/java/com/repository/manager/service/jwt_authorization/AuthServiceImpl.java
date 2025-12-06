package com.repository.manager.service.jwt_authorization;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.repository.manager.persistence.entity.Role;
import com.repository.manager.persistence.entity.Token;
import com.repository.manager.persistence.entity.User;
import com.repository.manager.persistence.repository.RoleRepository;
import com.repository.manager.persistence.repository.TokenRepository;
import com.repository.manager.persistence.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Token login(String email, String password) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		if (!authentication.isAuthenticated())
			throw new UsernameNotFoundException("Invalid credentials");

		User user = userRepository.findByEmail(email).orElse(null);
		Token jwtToken = user.getJwtToken();
		Token generatedToken = null;

		if (jwtToken != null) {
			Token token = tokenRepository.findById(jwtToken.getId()).orElse(null);
			if (token.getExpiresAt().after(new Date()))
				generatedToken = token;
			else {
				String tokenValue = jwtService.generateToken(email);
				generatedToken = saveJwtTokenToUser(user, tokenValue);
			}
		} else {
			String tokenValue = jwtService.generateToken(email);
			generatedToken = saveJwtTokenToUser(user, tokenValue);
		}
		return generatedToken;
	}

	public String addUser(User user) {
		Role role = roleRepository.findById(1L).orElse(null);

		User newUser = User.builder().email(user.getEmail()).password(passwordEncoder.encode(user.getPassword()))
				.roles(List.of(role)).build();
		userRepository.save(newUser);
		return "User added successfully";
	}

	public Token saveJwtTokenToUser(User user, String tokenValue) {
		Date tokenExpiration = jwtService.extractExpiration(tokenValue);
		Token token = Token.builder().value(tokenValue).expiresAt(tokenExpiration).build();

		user.setJwtToken(token);
		tokenRepository.save(token);
		userRepository.save(user);
		return token;
	}

}
