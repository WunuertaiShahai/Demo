package com.example.Demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

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
}