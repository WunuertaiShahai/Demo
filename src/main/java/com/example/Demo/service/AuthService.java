package com.example.Demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Demo.dto.RegistrationRequest;
import com.example.Demo.entity.User;
import com.example.Demo.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User registerUser(RegistrationRequest request) {
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new RuntimeException("用户名已存在");
		}

		if (userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("邮箱已被注册");
		}

		String encodedPassword = passwordEncoder.encode(request.getPassword());

		User user = new User(request.getUsername(), encodedPassword, request.getEmail());

		return userRepository.save(user);
	}
}