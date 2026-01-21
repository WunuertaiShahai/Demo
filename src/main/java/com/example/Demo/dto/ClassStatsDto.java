package com.example.Demo.dto;

public class ClassStatsDto {
    private int totalClasses;
    private int totalStudents;
    private int activeTeachers;
    private double avgStudentsPerClass;

    public ClassStatsDto() {}

    public ClassStatsDto(int totalClasses, int totalStudents, int activeTeachers, double avgStudentsPerClass) {
        this.totalClasses = totalClasses;
        this.totalStudents = totalStudents;
        this.activeTeachers = activeTeachers;
        this.avgStudentsPerClass = avgStudentsPerClass;
    }

    // Getter和Setter方法
    public int getTotalClasses() { return totalClasses; }
    public void setTotalClasses(int totalClasses) { this.totalClasses = totalClasses; }

    public int getTotalStudents() { return totalStudents; }
    public void setTotalStudents(int totalStudents) { this.totalStudents = totalStudents; }

    public int getActiveTeachers() { return activeTeachers; }
    public void setActiveTeachers(int activeTeachers) { this.activeTeachers = activeTeachers; }

    public double getAvgStudentsPerClass() { return avgStudentsPerClass; }
    public void setAvgStudentsPerClass(double avgStudentsPerClass) { this.avgStudentsPerClass = avgStudentsPerClass; }
}