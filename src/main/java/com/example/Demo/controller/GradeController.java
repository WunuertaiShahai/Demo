package com.example.Demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Demo.service.GradeService;

@Controller
@RequestMapping("/grade")
public class GradeController {

	@Autowired
	private GradeService gradeService;

	@GetMapping("/management/entry")
	public String showGradeEntryForm(Model model) {
		model.addAttribute("pageTitle", "成绩录入");
		model.addAttribute("courses", gradeService.getCourseList());
		model.addAttribute("grades", gradeService.getGradeList());
		return "management/grade_entry";
	}

	@PostMapping("/management/addGrade")
	public String addGrade(@RequestParam Map<String, Object> gradeData, Model model) {
		String studentId = gradeData.get("student_id").toString();
		if (!gradeService.checkStudentId(studentId)) {
			model.addAttribute("errorMessage", "学号不存在，请重新输入");
			return "management/grade_entry :: #errorMessage";
		}

		String courseId = gradeData.get("course").toString();
		if (!gradeService.checkCourseId(courseId)) {
			model.addAttribute("errorMessage", "课程不存在，请重新输入");
			return "management/grade_entry :: #errorMessage";
		}

		boolean success = gradeService.addGrade(gradeData);
		if (success) {
			model.addAttribute("successMessage", "成绩录入成功！");
		} else {
			model.addAttribute("errorMessage", "成绩录入失败，请检查数据！");
		}
		model.addAttribute("grades", gradeService.getGradeList());
		return "management/grade_entry :: #errorMessage";
	}
}