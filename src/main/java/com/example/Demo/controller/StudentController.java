package com.example.Demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Demo.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@GetMapping("/overview")
	public String showStudentOverview(Model model) {
		model.addAttribute("pageTitle", "学生信息总览");
		model.addAttribute("students", studentService.getStudentList());
		return "overview/student_overview";
	}

	@GetMapping("/management/add")
	public String showAddStudentForm(Model model) {
		model.addAttribute("pageTitle", "添加新学生");
		return "management/add_student";
	}

	@PostMapping("/management/add")
	public String addStudent(@RequestParam Map<String, Object> studentData, Model model) {
		boolean success = studentService.addStudent(studentData);
		if (success) {
			model.addAttribute("successMessage", "学生添加成功！");
		} else {
			model.addAttribute("errorMessage", "学生添加失败，请检查数据！");
		}
		return "management/add_student";
	}
}