package com.example.Demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Demo.service.StatisticsService;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

	@Autowired
	private StatisticsService statisticsService;

	@GetMapping("/overview")
	public String showStatisticsOverview(Model model) {
		model.addAttribute("pageTitle", "数据统计总览");

		// 获取统计数据
		model.addAttribute("totalStudents", statisticsService.getTotalStudents());
		model.addAttribute("totalClasses", statisticsService.getTotalClasses());
		model.addAttribute("totalTeachers", statisticsService.getTotalTeachers());
		model.addAttribute("totalCourses", statisticsService.getTotalCourses());

		// 获取图表数据
		model.addAttribute("scoreDistribution", statisticsService.getScoreDistribution());
		model.addAttribute("coursePopularity", statisticsService.getCoursePopularity());
		model.addAttribute("studentGrowth", statisticsService.getStudentGrowthData());
		model.addAttribute("gradeTrend", statisticsService.getGradeTrendData());

		return "management/statistics";
	}

	@GetMapping("/management")
	public String showStatisticsManagement(Model model) {
		model.addAttribute("pageTitle", "统计报告管理");
		model.addAttribute("reports", statisticsService.getStatisticsReports());
		return "statistics/statistics_management";
	}

	@PostMapping("/management/generate")
	public String generateStatisticsReport(@RequestParam Map<String, Object> reportConfig, Model model) {
		boolean success = statisticsService.generateStatisticsReport(reportConfig);
		if (success) {
			model.addAttribute("successMessage", "统计报告生成成功！");
		} else {
			model.addAttribute("errorMessage", "统计报告生成失败！");
		}
		model.addAttribute("reports", statisticsService.getStatisticsReports());
		return "statistics/statistics_management";
	}

	@GetMapping("/charts/score")
	@ResponseBody
	public Map<String, Object> getScoreChartData() {
		return statisticsService.getScoreChartData();
	}

	@GetMapping("/charts/course")
	@ResponseBody
	public Map<String, Object> getCourseChartData() {
		return statisticsService.getCourseChartData();
	}
}