package com.example.Demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.Demo.entity.Class;
import com.example.Demo.entity.College;
import com.example.Demo.entity.GradeYear;
import com.example.Demo.entity.Major;

@Service
public class StudentService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> getStudentList() {
		String sql = "SELECT s.*, c.class_name FROM student s LEFT JOIN class c ON s.class_id = c.class_id WHERE s.is_active = TRUE ORDER BY s.student_id LIMIT 50";
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

	/**
	 * 根据学院获取专业列表
	 */
	public List<Major> findMajorsByCollegeId(String collegeId) {

		String sql = "SELECT m.* FROM major m WHERE m.college_id = ? ORDER BY m.college_id, m.major_id";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, collegeId);

		List<Major> majors = new ArrayList<>();
		Major mjr = new Major();
		for (Map<String, Object> row : resultList) {
			mjr = new Major();
			mjr.setMajorId(row.get("major_id").toString());
			mjr.setMajorName(row.get("major_name").toString());
			mjr.setCollegeId(row.get("college_id").toString());
			majors.add(mjr);
		}

		return majors;
	}

	/**
	 * 根据专业获取班级列表
	 */
	public List<Class> findClassesByMajorId(String collegeId, String majorId, String grade) {

		String sql = "SELECT c.* FROM class c WHERE c.college_id = ? and c.major_id = ? and c.grade = ? ORDER BY c.class_id";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, collegeId, majorId, grade);

		List<Class> classes = new ArrayList<>();
		Class cls = new Class();
		for (Map<String, Object> row : resultList) {
			cls = new Class();
			cls.setClassId(row.get("class_id").toString());
			cls.setClassName(row.get("class_name").toString());
			classes.add(cls);
		}

		return classes;
	}

	/**
	 * 根据班级ID获取班级最大学号
	 */
	public String getMaxStudentId(String classId) {
		String sql = "SELECT c.max_student_id FROM class c WHERE c.class_id = ?";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, classId);
		String maxStudentId = "";
		if ((resultList != null) && (resultList.size() != 0) && (resultList.get(0).get("max_student_id") != null)) {
			maxStudentId = getFinalStudentId(resultList.get(0).get("max_student_id").toString());
		} else {
			maxStudentId = classId + "01";
		}
		return maxStudentId;
	}

	/**
	 * 根据最大学号获取最新的学号（最大学号+1）
	 */
	private String getFinalStudentId(String maxStudentId) {
		int fianlStudentId = Integer.parseInt(maxStudentId);
		fianlStudentId++;
		return String.valueOf(fianlStudentId);
	}

	public boolean addStudent(Map<String, Object> studentData) {

		String sql = "SELECT m.major_name FROM major m WHERE m.college_id = ? and m.major_id = ? ORDER BY m.college_id, m.major_id";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, studentData.get("college"),
				studentData.get("major"));
		String majorName = resultList.get(0).get("major_name").toString();

		sql = "INSERT INTO student (student_id, name, gender, birth_date, class_id, major, enrollment_year, phone, email, address, is_active) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, TRUE)";

		try {
			jdbcTemplate.update(sql, studentData.get("student_id"), studentData.get("name"), studentData.get("gender"),
					studentData.get("birthday"), studentData.get("class"), majorName, studentData.get("grade_year"),
					studentData.get("phone"), studentData.get("email"), studentData.get("address"));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}