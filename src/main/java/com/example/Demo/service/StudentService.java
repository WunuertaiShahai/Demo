package com.example.Demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.Demo.entity.College;
import com.example.Demo.entity.GradeYear;
import com.example.Demo.entity.Major;

@Service
public class StudentService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> getStudentList() {
		String sql = "SELECT s.*, c.class_name FROM student s LEFT JOIN class c ON s.class_id = c.id WHERE s.is_active = TRUE ORDER BY s.student_id LIMIT 50";
		return jdbcTemplate.queryForList(sql);
	}

	/**
	 * 年级列表获取
	 */
	public List<GradeYear> findGradeList() {

		String sql = "SELECT g.* FROM grade_year g ORDER BY g.gradeid";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);

		List<GradeYear> gradeYears = new ArrayList<>();
		for (Map<String, Object> row : resultList) {
			GradeYear grdYear = new GradeYear();
			grdYear.setGradeId(row.get("gradeid").toString());
			grdYear.setGradeName(row.get("gradename").toString());
			gradeYears.add(grdYear);
		}

		return gradeYears;
	}

	/**
	 * 学院列表获取
	 */
	public List<College> findCollegeList() {

		String sql = "SELECT c.* FROM college c ORDER BY c.college_id";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);

		List<College> colleges = new ArrayList<>();
		for (Map<String, Object> row : resultList) {
			College coll = new College();
			coll.setCollegeId(row.get("college_id").toString());
			coll.setCollegeName(row.get("college_name").toString());
			colleges.add(coll);
		}

		return colleges;
	}

	/**
	 * 专业列表获取
	 */
	public List<Major> findMajorList() {

		String sql = "SELECT m.* FROM major m ORDER BY m.college_id, m.major_id";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);

		List<Major> majors = new ArrayList<>();
		for (Map<String, Object> row : resultList) {
			Major mjr = new Major();
			mjr.setMajorId(row.get("major_id").toString());
			mjr.setMajorName(row.get("major_name").toString());
			mjr.setCollegeId(row.get("college_id").toString());
			majors.add(mjr);
		}

		return majors;
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