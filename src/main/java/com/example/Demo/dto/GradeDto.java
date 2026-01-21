package com.example.Demo.dto;

import java.time.LocalDate;

public class GradeDto {
    private String studentId;
    private String studentName;
    private String courseId;
    private String courseName;
    private Double score;
    private String examType;
    private LocalDate examDate;
    private String className;

    // 构造方法
    public GradeDto() {}

    // Getter和Setter方法
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public String getExamType() { return examType; }
    public void setExamType(String examType) { this.examType = examType; }

    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
}