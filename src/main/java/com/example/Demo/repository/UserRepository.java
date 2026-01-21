package com.example.Demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	// SQL方式1：使用 @Query 注解自定义SQL
	@Query("SELECT u FROM User u WHERE u.username = :username AND u.isActive = true")
	User findActiveUserByUsername(@Param("username") String username);

	// SQL方式2：使用原生SQL查询
	@Query(value = "SELECT * FROM sys_user WHERE username = :username AND is_active = 1", nativeQuery = true)
	User findActiveUserByUsernameNative(@Param("username") String username);

	// SQL方式3：包含密码验证的查询（不推荐，密码验证应在业务层）
	@Query("SELECT u FROM User u WHERE u.username = :username AND u.passwordHash = :passwordHash")
	User findByUsernameAndPassword(@Param("username") String username, @Param("passwordHash") String passwordHash);

	// 原有的自动生成方法
	User findByUsername(String username);

	User findByEmail(String email);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);
}