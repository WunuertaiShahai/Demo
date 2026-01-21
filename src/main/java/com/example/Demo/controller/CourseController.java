package com.example.Demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Demo.service.CourseService;

@Controller
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@GetMapping("/overview")
	public String showCourseOverview(Model model) {
		model.addAttribute("pageTitle", "课程信息总览");
		model.addAttribute("courses", courseService.getCourseList());
		return "overview/course_overview";
	}

	@GetMapping("/management/schedule")
	public String showCourseScheduleForm(Model model) {
		model.addAttribute("pageTitle", "课程安排");
		model.addAttribute("schedules", courseService.getCourseScheduleList());
		return "management/course_schedule";
	}

	@PostMapping("/management/schedule")
	public String addCourseSchedule(@RequestParam Map<String, Object> scheduleData, Model model) {
		boolean success = courseService.addCourseSchedule(scheduleData);
		if (success) {
			model.addAttribute("successMessage", "课程安排成功！");
		} else {
			model.addAttribute("errorMessage", "课程安排失败，请检查数据！");
		}
		model.addAttribute("schedules", courseService.getCourseScheduleList());
		return "management/course_schedule";
	}
}