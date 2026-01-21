package com.example.Demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GradeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getGradeList() {
        String sql = "SELECT g.*, s.name as student_name, c.course_name, cls.class_name " + 
                    "FROM grade g " +
                    "LEFT JOIN student s ON g.student_id = s.student_id " +
                    "LEFT JOIN course c ON g.course_id = c.id " +
                    "LEFT JOIN class cls ON s.class_id = cls.id " +
                    "ORDER BY g.exam_date DESC, g.score DESC " +
                    "LIMIT 50";
        return jdbcTemplate.queryForList(sql);
    }

    public boolean addGrade(Map<String, Object> gradeData) {
        String sql = "INSERT INTO grade (student_id, course_id, score, exam_type, exam_date) VALUES (?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql, gradeData.get("student_id"), gradeData.get("course_id"), gradeData.get("score"),
                    gradeData.get("exam_type"), gradeData.get("exam_date"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}