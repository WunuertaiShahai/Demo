package com.example.Demo.dto;

public class ClassDto {
    private String classCode;
    private String className;
    private String grade;
    private String department;
    private String major;
    private Long headTeacherId;
    private String headTeacherName;
    private Integer studentCount;

    // 构造方法
    public ClassDto() {}

    // Getter和Setter方法
    public String getClassCode() { return classCode; }
    public void setClassCode(String classCode) { this.classCode = classCode; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public Long getHeadTeacherId() { return headTeacherId; }
    public void setHeadTeacherId(Long headTeacherId) { this.headTeacherId = headTeacherId; }

    public String getHeadTeacherName() { return headTeacherName; }
    public void setHeadTeacherName(String headTeacherName) { this.headTeacherName = headTeacherName; }

    public Integer getStudentCount() { return studentCount; }
    public void setStudentCount(Integer studentCount) { this.studentCount = studentCount; }
}