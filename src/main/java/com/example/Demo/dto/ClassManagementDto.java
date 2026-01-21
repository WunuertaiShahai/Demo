package com.example.Demo.dto;

import java.time.LocalDateTime;

public class ClassManagementDto {
    private Long id;
    private String classCode;
    private String className;
    private String major;
    private String grade;
    private String department;
    private Long headTeacherId;
    private String headTeacherName;
    private Integer studentCount;
    private String status; // ACTIVE, INACTIVE
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 构造方法
    public ClassManagementDto() {}

    public ClassManagementDto(Long id, String classCode, String className, String major, 
                            String grade, String department, Long headTeacherId, 
                            String headTeacherName, Integer studentCount, String status) {
        this.id = id;
        this.classCode = classCode;
        this.className = className;
        this.major = major;
        this.grade = grade;
        this.department = department;
        this.headTeacherId = headTeacherId;
        this.headTeacherName = headTeacherName;
        this.studentCount = studentCount;
        this.status = status;
    }

    // Getter和Setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClassCode() { return classCode; }
    public void setClassCode(String classCode) { this.classCode = classCode; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Long getHeadTeacherId() { return headTeacherId; }
    public void setHeadTeacherId(Long headTeacherId) { this.headTeacherId = headTeacherId; }

    public String getHeadTeacherName() { return headTeacherName; }
    public void setHeadTeacherName(String headTeacherName) { this.headTeacherName = headTeacherName; }

    public Integer getStudentCount() { return studentCount; }
    public void setStudentCount(Integer studentCount) { this.studentCount = studentCount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}