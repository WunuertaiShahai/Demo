package com.example.Demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.Demo.service.DashboardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardTestController {

	@Autowired
	private DashboardService dashboardService;

	@GetMapping("/dashboardTest")
	public String showDashboard(Model model, HttpServletRequest request) {
		model.addAttribute("pageTitle", "学生信息管理系统 - 主画面");

		HttpSession session = request.getSession(false);
		String username = (session != null) ? (String) session.getAttribute("currentUsername") : null;

		if (username != null && !username.trim().isEmpty()) {
			model.addAttribute("welcomeUsername", username);
		} else {
			model.addAttribute("welcomeUsername", "用户");
		}

		model.addAttribute("totalStudents", dashboardService.getTotalStudents());
		model.addAttribute("totalClasses", dashboardService.getTotalClasses());
		model.addAttribute("totalTeachers", dashboardService.getTotalTeachers());
		model.addAttribute("totalCourses", dashboardService.getTotalCourses());

		return "dashboard";
	}

	@GetMapping("/helloTest")
	public String sayHello(Model model) {
		model.addAttribute("serverTime", java.time.LocalDateTime.now());
		return "home";
	}
}