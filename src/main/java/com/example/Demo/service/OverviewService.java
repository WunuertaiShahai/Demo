package com.example.Demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OverviewService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int getTotalStudents() {
        String sql = "SELECT COUNT(*) FROM student WHERE is_active = TRUE";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int getTotalClasses() {
        String sql = "SELECT COUNT(*) FROM class";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int getTotalTeachers() {
        String sql = "SELECT COUNT(*) FROM teacher WHERE is_active = TRUE";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int getTotalCourses() {
        String sql = "SELECT COUNT(*) FROM course";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<Map<String, Object>> getStudentList() {
        String sql = "SELECT s.*, c.class_name FROM student s LEFT JOIN class c ON s.class_id = c.id WHERE s.is_active = TRUE ORDER BY s.student_id LIMIT 50";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getClassList() {
        String sql = "SELECT c.*, t.name as head_teacher_name FROM class c LEFT JOIN teacher t ON c.head_teacher_id = t.id";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getTeacherList() {
        String sql = "SELECT * FROM teacher WHERE is_active = TRUE";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getCourseList() {
        String sql = "SELECT c.*, t.name as teacher_name FROM course c LEFT JOIN teacher t ON c.teacher_id = t.id";
        return jdbcTemplate.queryForList(sql);
    }
}