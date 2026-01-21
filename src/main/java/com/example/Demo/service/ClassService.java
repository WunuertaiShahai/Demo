package com.example.Demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClassService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getClassList() {
        String sql = "SELECT c.*, t.name as head_teacher_name, " +
                    "(SELECT COUNT(*) FROM student s WHERE s.class_id = c.id AND s.is_active = TRUE) as student_count " +
                    "FROM class c LEFT JOIN teacher t ON c.head_teacher_id = t.id " +
                    "ORDER BY c.grade DESC, c.class_name";
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> getClassById(Long id) {
        String sql = "SELECT c.*, t.name as head_teacher_name FROM class c " +
                    "LEFT JOIN teacher t ON c.head_teacher_id = t.id WHERE c.id = ?";
        return jdbcTemplate.queryForMap(sql, id);
    }

    public boolean addClass(Map<String, Object> classData) {
        String sql = "INSERT INTO class (class_code, class_name, grade, department, major, " +
                    "head_teacher_id, student_count, created_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

        try {
            jdbcTemplate.update(sql, 
                classData.get("class_code"), 
                classData.get("class_name"),
                classData.get("grade"), 
                classData.get("department"), 
                classData.get("major"),
                classData.get("head_teacher_id"), 
                0  // 初始学生数为0
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateClass(Long id, Map<String, Object> classData) {
        String sql = "UPDATE class SET class_code = ?, class_name = ?, grade = ?, " +
                    "department = ?, major = ?, head_teacher_id = ?, updated_at = NOW() " +
                    "WHERE id = ?";

        try {
            jdbcTemplate.update(sql, 
                classData.get("class_code"), 
                classData.get("class_name"),
                classData.get("grade"), 
                classData.get("department"), 
                classData.get("major"),
                classData.get("head_teacher_id"), 
                id
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getStudentCountByClassId(Long classId) {
        String sql = "SELECT COUNT(*) FROM student WHERE class_id = ? AND is_active = TRUE";
        return jdbcTemplate.queryForObject(sql, Integer.class, classId);
    }
}