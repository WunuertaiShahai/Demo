package com.example.Demo.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.example.Demo.dto.ClassManagementDto;
import com.example.Demo.dto.ClassStatsDto;

@Service
public class ClassManagementService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 获取所有班级列表
	public List<ClassManagementDto> getAllClasses() {
		String sql = "SELECT c.class_id, c.class_name, c.major_id, c.grade, c.college_id, "
				+ "( SELECT COUNT(*) FROM student s WHERE s.class_id = c.class_id AND s.is_active = TRUE ) AS student_count, "
				+ "CASE WHEN c.is_active = TRUE THEN 'ACTIVE' ELSE 'INACTIVE' END AS STATUS, c.created_at, c.updated_at "
				+ "FROM class c ORDER BY c.grade DESC, c.class_name";

		return jdbcTemplate.query(sql, new ClassManagementRowMapper());
	}

	// 根据ID获取班级
	public ClassManagementDto getClassById(Long id) {
		String sql = "SELECT c.class_id, c.class_name, c.college_id, c.major_id, c.grade, "
				+ "(SELECT COUNT(*) FROM student s WHERE s.class_id = c.id AND s.is_active = TRUE) as student_count, "
				+ "CASE WHEN c.is_active = 1 THEN 'ACTIVE' ELSE 'INACTIVE' END as status " + "FROM class c "
				+ "WHERE c.class_id = ?";

		return jdbcTemplate.queryForObject(sql, new ClassManagementRowMapper(), id);
	}

	// 添加班级
	public boolean addClass(ClassManagementDto classDto) {
		String sql = "INSERT INTO class (class_id, class_name, college_id, major_id, grade, "
				+ "student_count, is_active, created_at) " + "VALUES (?, ?, ?, ?, ?, ?, 1, NOW())";

		try {
			jdbcTemplate.update(sql, classDto.getClassId(), classDto.getClassName(), classDto.getCollegeId(),
					classDto.getMajorId(), classDto.getGrade(), 0 // 初始学生数为0
			);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 更新班级
	public boolean updateClass(ClassManagementDto classDto) {
		String sql = "UPDATE class SET lass_name = ?, college_id = ?, major_id = ?, grade = ?, updated_at = NOW() "
				+ "WHERE class_id = ?";

		try {
			jdbcTemplate.update(sql, classDto.getClassName(), classDto.getCollegeId(), classDto.getMajorId(),
					classDto.getGrade(), classDto.getClassId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 删除班级（软删除）
	public boolean deleteClass(Long id) {
		String sql = "UPDATE class SET is_active = FALSE, updated_at = NOW() WHERE class_id = ?";

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
		String sql = "SELECT t.teacher_id, t.teacher_name FROM teacher t WHERE t.is_active = false ORDER BY t.teacher_id";
		return jdbcTemplate.queryForList(sql);
	}

	// RowMapper实现
	private static class ClassManagementRowMapper implements RowMapper<ClassManagementDto> {
		@Override
		public ClassManagementDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassManagementDto dto = new ClassManagementDto();
			dto.setClassId(rs.getString("class_id"));
			dto.setClassName(rs.getString("class_name"));
			dto.setCollegeId(rs.getString("college_id"));
			dto.setMajorId(rs.getString("major_id"));
			dto.setGrade(rs.getString("grade"));
			dto.setStudentCount(rs.getInt("student_count"));
			dto.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			if (rs.getTimestamp("updated_at") != null) {
				dto.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
			}
			return dto;
		}
	}
}