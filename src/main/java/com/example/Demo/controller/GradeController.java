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
		model.addAttribute("grades", gradeService.getGradeList());
		return "management/grade_entry";
	}

	@PostMapping("/management/entry")
	public String addGrade(@RequestParam Map<String, Object> gradeData, Model model) {
		boolean success = gradeService.addGrade(gradeData);
		if (success) {
			model.addAttribute("successMessage", "成绩录入成功！");
		} else {
			model.addAttribute("errorMessage", "成绩录入失败，请检查数据！");
		}
		model.addAttribute("grades", gradeService.getGradeList());
		return "management/grade_entry";
	}
}