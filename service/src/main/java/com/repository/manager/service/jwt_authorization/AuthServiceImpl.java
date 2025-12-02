package com.repository.manager.service.jwt_authorization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.repository.manager.persistence.entity.Role;
import com.repository.manager.persistence.entity.User;
import com.repository.manager.persistence.repository.RoleRepository;
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
	private PasswordEncoder passwordEncoder;

	@Override
	public String login(String email, String password) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		if (authentication.isAuthenticated())
			return jwtService.generateToken(email);
		else
			throw new UsernameNotFoundException("Invalid credentials");
	}

	public String addUser(User user) {
		Role role = roleRepository.findById(1L).orElse(null);

		User newUser = User.builder().email(user.getEmail()).password(passwordEncoder.encode(user.getPassword()))
				.roles(List.of(role)).build();
		userRepository.save(newUser);
		return "User added successfully";
	}

}
