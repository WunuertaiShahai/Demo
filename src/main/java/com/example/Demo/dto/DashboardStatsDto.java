package com.example.Demo.dto;

public class DashboardStatsDto {
    private int totalStudents;
    private int totalClasses;
    private int totalTeachers;
    private int totalCourses;

    public DashboardStatsDto() {}

    public DashboardStatsDto(int totalStudents, int totalClasses, int totalTeachers, int totalCourses) {
        this.totalStudents = totalStudents;
        this.totalClasses = totalClasses;
        this.totalTeachers = totalTeachers;
        this.totalCourses = totalCourses;
    }

    // Getter和Setter方法
    public int getTotalStudents() { return totalStudents; }
    public void setTotalStudents(int totalStudents) { this.totalStudents = totalStudents; }

    public int getTotalClasses() { return totalClasses; }
    public void setTotalClasses(int totalClasses) { this.totalClasses = totalClasses; }

    public int getTotalTeachers() { return totalTeachers; }
    public void setTotalTeachers(int totalTeachers) { this.totalTeachers = totalTeachers; }

    public int getTotalCourses() { return totalCourses; }
    public void setTotalCourses(int totalCourses) { this.totalCourses = totalCourses; }
}