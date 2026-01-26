package com.example.Demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Demo.entity.Class;
import com.example.Demo.entity.College;
import com.example.Demo.entity.Major;
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

		/*
		 * List<GradeYear> gradeList = studentService.findGradeList();
		 * model.addAttribute("gradeList", gradeList);
		 */
		List<Integer> gradeList = generateYearRange();
		model.addAttribute("gradeList", gradeList);
		// 设置默认年份为当前年份
		int currentYear = java.time.Year.now().getValue();
		model.addAttribute("defaultYear", currentYear);

		List<College> collegeList = studentService.findCollegeList();
		model.addAttribute("collegeList", collegeList);

//		List<Major> majorList = studentService.findMajorList();
//		model.addAttribute("majorList", majorList);

		model.addAttribute("pageTitle", "添加新学生");
		return "management/add_student";
	}

	/**
	 * 生成年份范围：当前年份往前5年，往后5年
	 */
	private List<Integer> generateYearRange() {
		List<Integer> years = new ArrayList<>();
		int currentYear = java.time.Year.now().getValue();

		for (int i = currentYear - 5; i <= currentYear; i++) {
			years.add(i);
		}
		return years;
	}

	@GetMapping("/college/majors")
	public String getMajorsByCollege(@RequestParam("collegeId") String collegeId, Model model) {
		// 1. 根据collegeId从数据库查询对应的专业列表
		List<Major> majorList = studentService.findMajorsByCollegeId(collegeId);

		// 2. 将数据添加到Model中，供Thymeleaf模板使用
		model.addAttribute("majorList", majorList);

		// 3. 返回Thymeleaf片段
		// "page-name"是你的HTML模板文件名（位于templates目录下）
		// "fragment-name"是你在模板中定义的片段名称
		return "management/add_student :: #major";
	}

	@GetMapping("/college/classes")
	public String getClassesByMajor(@RequestParam("collegeId") String collegeId,
			@RequestParam("majorId") String majorId, @RequestParam("grade") String grade, Model model) {
		// 1. 根据majorId从数据库查询对应的班级列表
		List<Class> classList = studentService.findClassesByMajorId(collegeId, majorId, grade);

		// 2. 将数据添加到Model中，供Thymeleaf模板使用
		model.addAttribute("classList", classList);
		if ((classList != null) && (classList.size() != 0)) {
			model.addAttribute("maxStudentId", classList.get(0).getMaxStudentId());
		} else {
			model.addAttribute("maxStudentId", "");
		}

		// 3. 返回Thymeleaf片段
		return "management/add_student :: #class";
	}

	@GetMapping("/college/maxStudentId")
	@ResponseBody
	public String getMaxStudentId(@RequestParam("classId") String classId, Model model) {
		// 1. 根据majorId从数据库查询对应的班级列表
		String maxStudentId = studentService.getMaxStudentId(classId);

		// 2. 将数据添加到Model中，供Thymeleaf模板使用
		model.addAttribute("maxStudentId", maxStudentId);

		return maxStudentId != null ? maxStudentId : "";

		// 3. 返回Thymeleaf片段
		/* return "management/add_student :: #student_id"; */
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