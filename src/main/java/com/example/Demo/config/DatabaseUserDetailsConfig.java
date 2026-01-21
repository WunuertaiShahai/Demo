// DatabaseUserDetailsConfig.java
package com.example.Demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.Demo.entity.User;
import com.example.Demo.repository.UserRepository;

@Configuration
public class DatabaseUserDetailsConfig {

	@Autowired
	private UserRepository userRepository;

	@Bean
	public UserDetailsService userDetailsService() {
		return username -> {
			// 从数据库查询用户
			User user = userRepository.findActiveUserByUsername(username);

			if (user == null) {
				throw new UsernameNotFoundException("用户不存在: " + username);
			}

			// 检查用户是否激活
			if (user.getIsActive() == null || !user.getIsActive()) {
				throw new UsernameNotFoundException("用户已被禁用: " + username);
			}

			// 创建Spring Security的UserDetails对象
			return org.springframework.security.core.userdetails.User.builder().username(user.getUsername())
					.password(user.getPasswordHash()).roles("USER").build();
		};
	}
}