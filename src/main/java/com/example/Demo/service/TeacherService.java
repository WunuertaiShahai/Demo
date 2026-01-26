package com.example.Demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> getTeacherList() {
		String sql = "SELECT t.*, d.department_name, "
				+ "(SELECT COUNT(*) FROM course c WHERE c.teacher_id = t.teacher_id) as course_count "
				+ "FROM teacher t LEFT JOIN department d ON t.department_id = d.id "
				+ "WHERE t.is_active = TRUE ORDER BY t.teacher_id";
		return jdbcTemplate.queryForList(sql);
	}

	public Map<String, Object> getTeacherById(Long id) {
		String sql = "SELECT t.*, d.department_name FROM teacher t "
				+ "LEFT JOIN department d ON t.department_id = d.id WHERE t.teacher_id = ?";
		return jdbcTemplate.queryForMap(sql, id);
	}

	public List<Map<String, Object>> getCoursesByTeacherId(Long teacherId) {
		String sql = "SELECT c.*, cs.classroom, cs.day_of_week, cs.start_time, cs.end_time "
				+ "FROM course c LEFT JOIN course_schedule cs ON c.id = cs.course_id "
				+ "WHERE c.teacher_id = ? ORDER BY cs.day_of_week, cs.start_time";
		return jdbcTemplate.queryForList(sql, teacherId);
	}

	public boolean addTeacher(Map<String, Object> teacherData) {
		String sql = "INSERT INTO teacher (teacher_id, name, gender, department_id, title, "
				+ "phone, email, office, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, TRUE)";

		try {
			jdbcTemplate.update(sql, teacherData.get("teacher_id"), teacherData.get("name"), teacherData.get("gender"),
					teacherData.get("department_id"), teacherData.get("title"), teacherData.get("phone"),
					teacherData.get("email"), teacherData.get("office"));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateTeacher(Long id, Map<String, Object> teacherData) {
		String sql = "UPDATE teacher SET teacher_id = ?, name = ?, gender = ?, "
				+ "department_id = ?, title = ?, phone = ?, email = ?, office = ? " + "WHERE id = ?";

		try {
			jdbcTemplate.update(sql, teacherData.get("teacher_id"), teacherData.get("name"), teacherData.get("gender"),
					teacherData.get("department_id"), teacherData.get("title"), teacherData.get("phone"),
					teacherData.get("email"), teacherData.get("office"), id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deactivateTeacher(Long id) {
		String sql = "UPDATE teacher SET is_active = FALSE WHERE id = ?";
		try {
			jdbcTemplate.update(sql, id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}