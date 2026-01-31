package com.example.Demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> getCourseList() {
		String sql = "SELECT c.* FROM course c";
		return jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> getClassList() {
		String sql = "SELECT c.class_id, c.class_name FROM class c";
		return jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> getTeacherList() {
		String sql = "SELECT t.teacher_id, t.teacher_name FROM teacher t";
		return jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> getCourseScheduleList() {
		String sql = "SELECT cs.*, c.course_name, cls.class_name, t.teacher_name AS teacher_name "
				+ "FROM course_schedule cs " + "LEFT JOIN course c ON cs.course_id = c.course_id "
				+ "LEFT JOIN class cls ON cs.class_id = cls.class_id "
				+ "LEFT JOIN teacher t ON cs.teacher_id = t.teacher_id " + "ORDER BY cs.day_of_week, cs.start_time";
		return jdbcTemplate.queryForList(sql);
	}

	public boolean addCourseSchedule(Map<String, Object> scheduleData) {
		String sql = "INSERT INTO course_schedule (course_id, class_id, teacher_id, classroom, day_of_week, start_time, end_time, semester, academic_year) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			jdbcTemplate.update(sql, scheduleData.get("course_id"), scheduleData.get("class_id"),
					scheduleData.get("teacher_id"), scheduleData.get("classroom"), scheduleData.get("day_of_week"),
					scheduleData.get("start_time"), scheduleData.get("end_time"), scheduleData.get("semester"),
					scheduleData.get("academic_year"));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}