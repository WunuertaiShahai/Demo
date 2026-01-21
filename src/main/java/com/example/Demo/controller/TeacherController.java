package com.example.Demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.Demo.service.TeacherService;

import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/overview")
    public String showTeacherOverview(Model model) {
        model.addAttribute("pageTitle", "教师信息总览");
        model.addAttribute("teachers", teacherService.getTeacherList());
        return "overview/teacher_overview";
    }

    @GetMapping("/management/add")
    public String showAddTeacherForm(Model model) {
        model.addAttribute("pageTitle", "添加新教师");
        return "management/add_teacher";
    }

    @PostMapping("/management/add")
    public String addTeacher(@RequestParam Map<String, Object> teacherData, Model model) {
        boolean success = teacherService.addTeacher(teacherData);
        if (success) {
            model.addAttribute("successMessage", "教师添加成功！");
        } else {
            model.addAttribute("errorMessage", "教师添加失败，请检查数据！");
        }
        return "management/add_teacher";
    }

    @GetMapping("/management/edit/{id}")
    public String showEditTeacherForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pageTitle", "编辑教师信息");
        model.addAttribute("teacher", teacherService.getTeacherById(id));
        return "management/edit_teacher";
    }

    @PostMapping("/management/edit/{id}")
    public String updateTeacher(@PathVariable("id") Long id, 
                              @RequestParam Map<String, Object> teacherData, Model model) {
        boolean success = teacherService.updateTeacher(id, teacherData);
        if (success) {
            model.addAttribute("successMessage", "教师信息更新成功！");
        } else {
            model.addAttribute("errorMessage", "教师信息更新失败！");
        }
        model.addAttribute("teacher", teacherService.getTeacherById(id));
        return "management/edit_teacher";
    }

    @GetMapping("/management/courses/{teacherId}")
    public String showTeacherCourses(@PathVariable("teacherId") Long teacherId, Model model) {
        model.addAttribute("pageTitle", "教师授课信息");
        model.addAttribute("teacher", teacherService.getTeacherById(teacherId));
        model.addAttribute("courses", teacherService.getCoursesByTeacherId(teacherId));
        return "management/teacher_courses";
    }
}