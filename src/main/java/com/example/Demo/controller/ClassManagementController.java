package com.example.Demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Demo.dto.ClassManagementDto;
import com.example.Demo.dto.ClassStatsDto;
import com.example.Demo.service.ClassManagementService;

@Controller
@RequestMapping("/class/management")
public class ClassManagementController {

	@Autowired
	private ClassManagementService classManagementService;

	@GetMapping("/menu")
	public String showClassManagementDashboard(Model model) {
		// 获取统计数据
		model.addAttribute("totalClasses", classManagementService.getTotalClassCount());
		model.addAttribute("totalStudents", classManagementService.getTotalStudentCount());
		model.addAttribute("activeTeachers", classManagementService.getActiveTeacherCount());
		model.addAttribute("avgStudentsPerClass", classManagementService.getAvgStudentsPerClass());

		// 获取班级列表
		model.addAttribute("classList", classManagementService.getAllClasses());

		// 获取教师列表（用于班主任选择）
		model.addAttribute("teachers", classManagementService.getActiveTeachers());

		return "menu/class_management";
	}

	@GetMapping("/api/list")
	@ResponseBody
	public List<ClassManagementDto> getClassList() {
		return classManagementService.getAllClasses();
	}

	@GetMapping("/api/{id}")
	@ResponseBody
	public ClassManagementDto getClassById(@PathVariable Long id) {
		return classManagementService.getClassById(id);
	}

	@PostMapping("/api/add")
	@ResponseBody
	public String addClass(@RequestBody ClassManagementDto classDto) {
		boolean success = classManagementService.addClass(classDto);
		return success ? "SUCCESS" : "FAILED";
	}

	@PutMapping("/api/update")
	@ResponseBody
	public String updateClass(@RequestBody ClassManagementDto classDto) {
		boolean success = classManagementService.updateClass(classDto);
		return success ? "SUCCESS" : "FAILED";
	}

	@DeleteMapping("/api/delete/{id}")
	@ResponseBody
	public String deleteClass(@PathVariable Long id) {
		boolean success = classManagementService.deleteClass(id);
		return success ? "SUCCESS" : "FAILED";
	}

	@GetMapping("/api/stats")
	@ResponseBody
	public ClassStatsDto getClassStats() {
		return classManagementService.getClassStats();
	}
}