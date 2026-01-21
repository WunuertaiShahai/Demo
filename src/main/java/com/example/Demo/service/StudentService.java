package com.example.Demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getStudentList() {
        String sql = "SELECT s.*, c.class_name FROM student s LEFT JOIN class c ON s.class_id = c.id WHERE s.is_active = TRUE ORDER BY s.student_id LIMIT 50";
        return jdbcTemplate.queryForList(sql);
    }

    public boolean addStudent(Map<String, Object> studentData) {
        String sql = "INSERT INTO student (student_id, name, gender, class_id, major, enrollment_year, phone, email, address, is_active) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, TRUE)";

        try {
            jdbcTemplate.update(sql, studentData.get("student_id"), studentData.get("name"), studentData.get("gender"),
                    studentData.get("class_id"), studentData.get("major"), studentData.get("enrollment_year"),
                    studentData.get("phone"), studentData.get("email"), studentData.get("address"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}