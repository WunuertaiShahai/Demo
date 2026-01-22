package com.example.Demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "grade_year")
public class GradeYear {

	@Id
	@Column(name = "grade_id", unique = true, nullable = false)
	private String gradeId;

	@Column(nullable = false)
	private String gradeName;

	// 构造方法、getter和setter
	public GradeYear() {
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
}