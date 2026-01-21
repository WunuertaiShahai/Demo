package com.example.Demo.dto;

//根据您的Spring Boot版本选择导入（Spring Boot 3.x 使用 jakarta，旧版使用 javax）
import jakarta.validation.constraints.*;

public class RegistrationRequest {

 @NotBlank(message = "用户名不能为空")
 @Size(min = 3, max = 20, message = "用户名长度必须在3到20个字符之间")
 private String username;

 @NotBlank(message = "邮箱不能为空")
 @Email(message = "邮箱格式不正确")
 private String email;

 @NotBlank(message = "密码不能为空")
 @Size(min = 6, message = "密码至少6位")
 private String password;

 // 确认密码字段，用于在前端验证两次输入是否一致
 private String confirmPassword;

 // 以下是必须的 Getter 和 Setter 方法（可用IDE一键生成）
 public String getUsername() { return username; }
 public void setUsername(String username) { this.username = username; }

 public String getEmail() { return email; }
 public void setEmail(String email) { this.email = email; }

 public String getPassword() { return password; }
 public void setPassword(String password) { this.password = password; }

 public String getConfirmPassword() { return confirmPassword; }
 public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}
