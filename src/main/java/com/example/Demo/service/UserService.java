package com.example.Demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Demo.dto.RegistrationRequest;
import com.example.Demo.entity.User;
import com.example.Demo.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User registerUser(RegistrationRequest request) {
        // 验证用户名是否已存在
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 验证邮箱是否已注册
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("邮箱已被注册");
        }
        
        // 密码加密
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        
        // 创建用户实体
        User user = new User(
            request.getUsername(),
            encodedPassword,
            request.getEmail()
        );
        
        return userRepository.save(user);
    }
}