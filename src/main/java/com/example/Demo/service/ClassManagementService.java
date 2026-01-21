package com.example.Demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.example.Demo.dto.ClassManagementDto;
import com.example.Demo.dto.ClassStatsDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class ClassManagementService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 获取所有班级列表
    public List<ClassManagementDto> getAllClasses() {
        String sql = "SELECT c.id, c.class_code, c.class_name, c.major, c.grade, c.department, " +
                    "c.head_teacher_id, t.name as head_teacher_name, " +
                    "(SELECT COUNT(*) FROM student s WHERE s.class_id = c.id AND s.is_active = TRUE) as student_count, " +
                    "CASE WHEN c.is_active = TRUE THEN 'ACTIVE' ELSE 'INACTIVE' END as status, " +
                    "c.created_at, c.updated_at " +
                    "FROM class c " +
                    "LEFT JOIN teacher t ON c.head_teacher_id = t.id " +
                    "ORDER BY c.grade DESC, c.class_name";

        return jdbcTemplate.query(sql, new ClassManagementRowMapper());
    }

    // 根据ID获取班级
    public ClassManagementDto getClassById(Long id) {
        String sql = "SELECT c.id, c.class_code, c.class_name, c.major, c.grade, c.department, " +
                    "c.head_teacher_id, t.name as head_teacher_name, " +
                    "(SELECT COUNT(*) FROM student s WHERE s.class_id = c.id AND s.is_active = TRUE) as student_count, " +
                    "CASE WHEN c.is_active = TRUE THEN 'ACTIVE' ELSE 'INACTIVE' END as status " +
                    "FROM class c " +
                    "LEFT JOIN teacher t ON c.head_teacher_id = t.id " +
                    "WHERE c.id = ?";

        return jdbcTemplate.queryForObject(sql, new ClassManagementRowMapper(), id);
    }

    // 添加班级
    public boolean addClass(ClassManagementDto classDto) {
        String sql = "INSERT INTO class (class_code, class_name, major, grade, department, " +
                    "head_teacher_id, student_count, is_active, created_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, TRUE, NOW())";

        try {
            jdbcTemplate.update(sql, 
                classDto.getClassCode(), 
                classDto.getClassName(),
                classDto.getMajor(), 
                classDto.getGrade(), 
                classDto.getDepartment(),
                classDto.getHeadTeacherId(), 
                0  // 初始学生数为0
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 更新班级
    public boolean updateClass(ClassManagementDto classDto) {
        String sql = "UPDATE class SET class_code = ?, class_name = ?, major = ?, grade = ?, " +
                    "department = ?, head_teacher_id = ?, updated_at = NOW() " +
                    "WHERE id = ?";

        try {
            jdbcTemplate.update(sql, 
                classDto.getClassCode(), 
                classDto.getClassName(),
                classDto.getMajor(), 
                classDto.getGrade(), 
                classDto.getDepartment(),
                classDto.getHeadTeacherId(), 
                classDto.getId()
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 删除班级（软删除）
    public boolean deleteClass(Long id) {
        String sql = "UPDATE class SET is_active = FALSE, updated_at = NOW() WHERE id = ?";
        
        try {
            jdbcTemplate.update(sql, id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 获取统计数据
    public ClassStatsDto getClassStats() {
        int totalClasses = getTotalClassCount();
        int totalStudents = getTotalStudentCount();
        int activeTeachers = getActiveTeacherCount();
        double avgStudentsPerClass = totalClasses > 0 ? (double) totalStudents / totalClasses : 0;

        return new ClassStatsDto(totalClasses, totalStudents, activeTeachers, avgStudentsPerClass);
    }

    public int getTotalClassCount() {
        String sql = "SELECT COUNT(*) FROM class WHERE is_active = TRUE";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int getTotalStudentCount() {
        String sql = "SELECT COUNT(*) FROM student WHERE is_active = TRUE";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int getActiveTeacherCount() {
        String sql = "SELECT COUNT(*) FROM teacher WHERE is_active = TRUE";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public double getAvgStudentsPerClass() {
        int totalClasses = getTotalClassCount();
        int totalStudents = getTotalStudentCount();
        return totalClasses > 0 ? (double) totalStudents / totalClasses : 0;
    }

    // 获取活跃教师列表
    public List<Map<String, Object>> getActiveTeachers() {
        String sql = "SELECT id, name FROM teacher WHERE is_active = TRUE ORDER BY name";
        return jdbcTemplate.queryForList(sql);
    }

    // RowMapper实现
    private static class ClassManagementRowMapper implements RowMapper<ClassManagementDto> {
        @Override
        public ClassManagementDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            ClassManagementDto dto = new ClassManagementDto();
            dto.setId(rs.getLong("id"));
            dto.setClassCode(rs.getString("class_code"));
            dto.setClassName(rs.getString("class_name"));
            dto.setMajor(rs.getString("major"));
            dto.setGrade(rs.getString("grade"));
            dto.setDepartment(rs.getString("department"));
            dto.setHeadTeacherId(rs.getLong("head_teacher_id"));
            dto.setHeadTeacherName(rs.getString("head_teacher_name"));
            dto.setStudentCount(rs.getInt("student_count"));
            dto.setStatus(rs.getString("status"));
            dto.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            if (rs.getTimestamp("updated_at") != null) {
                dto.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
            return dto;
        }
    }
}