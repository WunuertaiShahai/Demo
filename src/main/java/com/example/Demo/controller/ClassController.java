package com.example.Demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.Demo.service.ClassService;

import java.util.Map;

@Controller
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping("/overview")
    public String showClassOverview(Model model) {
        model.addAttribute("pageTitle", "班级信息总览");
        model.addAttribute("classes", classService.getClassList());
        return "overview/class_overview";
    }

    @GetMapping("/management/add")
    public String showAddClassForm(Model model) {
        model.addAttribute("pageTitle", "添加新班级");
        return "management/add_class";
    }

    @PostMapping("/management/add")
    public String addClass(@RequestParam Map<String, Object> classData, Model model) {
        boolean success = classService.addClass(classData);
        if (success) {
            model.addAttribute("successMessage", "班级添加成功！");
        } else {
            model.addAttribute("errorMessage", "班级添加失败，请检查数据！");
        }
        return "management/add_class";
    }

    @GetMapping("/management/edit/{id}")
    public String showEditClassForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pageTitle", "编辑班级信息");
        model.addAttribute("classInfo", classService.getClassById(id));
        return "management/edit_class";
    }

    @PostMapping("/management/edit/{id}")
    public String updateClass(@PathVariable("id") Long id, 
                            @RequestParam Map<String, Object> classData, Model model) {
        boolean success = classService.updateClass(id, classData);
        if (success) {
            model.addAttribute("successMessage", "班级信息更新成功！");
        } else {
            model.addAttribute("errorMessage", "班级信息更新失败！");
        }
        model.addAttribute("classInfo", classService.getClassById(id));
        return "management/edit_class";
    }
}