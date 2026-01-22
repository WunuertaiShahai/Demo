package com.example.Demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "major")
public class Major {

	@Id
	@Column(name = "major_id", nullable = false)
	private String majorId;

	@Column(nullable = false)
	private String majorName;

	@Column(nullable = false)
	private String collegeId;

	// 构造方法、getter和setter
	public Major() {
	}

	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(String collegeId) {
		this.collegeId = collegeId;
	}
}