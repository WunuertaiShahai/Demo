package com.example.Demo.controller; // 请替换为您的实际包名

import com.example.Demo.dto.RegistrationRequest;
import com.example.Demo.entity.User;
import com.example.Demo.repository.UserRepository;
import com.example.Demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "register";
    }
    
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute RegistrationRequest request,
                              BindingResult result,
                              Model model) {
        // 前端验证
        if (result.hasErrors()) {
            return "register";
        }
        
        // 密码确认验证
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            model.addAttribute("error", "密码不匹配");
            return "register";
        }
        
        try {
            userService.registerUser(request);
            model.addAttribute("success", "注册成功，请登录");
            return "redirect:/auth/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}