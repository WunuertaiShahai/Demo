package com.example.Demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class GradeService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> getGradeList() {
		String sql = "SELECT g.*, s.NAME AS student_name, c.course_name, cls.class_name "
				+ "FROM grade g LEFT JOIN student s ON g.student_id = s.student_id "
				+ "LEFT JOIN course c ON g.course_id = c.course_id "
				+ "LEFT JOIN class cls ON s.class_id = cls.class_id " + "ORDER BY g.exam_date DESC, g.score DESC";
		return jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> getCourseList() {
		String sql = "SELECT c.course_id, c.course_name FROM course c";
		return jdbcTemplate.queryForList(sql);
	}

	public boolean addGrade(Map<String, Object> gradeData) {
		String sql = "INSERT INTO grade (student_id, course_id, score, exam_type, exam_date) VALUES (?, ?, ?, ?, ?)";

		try {
			jdbcTemplate.update(sql, gradeData.get("student_id"), gradeData.get("course"), gradeData.get("score"),
					gradeData.get("exam_type"), gradeData.get("exam_date"));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean checkStudentId(String studentId) {
		String sql = "SELECT s.student_id FROM student s WHERE s.student_id = ?";

		try {
			List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, studentId);

			if ((resultList != null) && (resultList.size() != 0) && (resultList.get(0) != null)) {
				String id = resultList.get(0).get("student_id").toString();
				if (studentId.equals(id)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean checkCourseId(String courseId) {
		String sql = "SELECT c.course_id FROM course c WHERE c.course_id = ?";

		try {
			List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, courseId);

			if ((resultList != null) && (resultList.size() != 0) && (resultList.get(0) != null)) {
				String id = resultList.get(0).get("course_id").toString();
				if (courseId.equals(id)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}