package com.example.Demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Demo.dto.RegistrationRequest;
import com.example.Demo.service.AuthService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("registrationRequest", new RegistrationRequest());
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute RegistrationRequest request, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "register";
		}

		if (!request.getPassword().equals(request.getConfirmPassword())) {
			model.addAttribute("error", "两次密码不一致");
			return "register";
		}

		try {
			authService.registerUser(request);
			return "redirect:/auth/register/success";
		} catch (RuntimeException e) {
			model.addAttribute("error", e.getMessage());
			return "register";
		}
	}

	@GetMapping("/register/success")
	public String showRegistrationSuccess() {
		return "register_finish";
	}
}