package com.example.Demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int getTotalStudents() {
        String sql = "SELECT COUNT(*) FROM student WHERE is_active = TRUE";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int getTotalClasses() {
        String sql = "SELECT COUNT(*) FROM class";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int getTotalTeachers() {
        String sql = "SELECT COUNT(*) FROM teacher WHERE is_active = TRUE";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int getTotalCourses() {
        String sql = "SELECT COUNT(*) FROM course";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Map<String, Integer> getScoreDistribution() {
        String sql = "SELECT " +
                "SUM(CASE WHEN score >= 90 THEN 1 ELSE 0 END) as excellent, " +
                "SUM(CASE WHEN score >= 80 AND score < 90 THEN 1 ELSE 0 END) as good, " +
                "SUM(CASE WHEN score >= 70 AND score < 80 THEN 1 ELSE 0 END) as average, " +
                "SUM(CASE WHEN score >= 60 AND score < 70 THEN 1 ELSE 0 END) as poor, " +
                "SUM(CASE WHEN score < 60 THEN 1 ELSE 0 END) as fail " +
                "FROM grade WHERE exam_type = '期末'";
        
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Map<String, Integer> distribution = new HashMap<>();
            distribution.put("excellent", rs.getInt("excellent"));
            distribution.put("good", rs.getInt("good"));
            distribution.put("average", rs.getInt("average"));
            distribution.put("poor", rs.getInt("poor"));
            distribution.put("fail", rs.getInt("fail"));
            return distribution;
        });
    }

    public Map<String, Integer> getCoursePopularity() {
        String sql = "SELECT c.course_name, COUNT(g.id) as student_count " +
                "FROM course c LEFT JOIN grade g ON c.id = g.course_id " +
                "GROUP BY c.id, c.course_name ORDER BY student_count DESC LIMIT 10";
        
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        Map<String, Integer> popularity = new LinkedHashMap<>();
        
        for (Map<String, Object> row : result) {
            popularity.put((String) row.get("course_name"), ((Long) row.get("student_count")).intValue());
        }
        
        return popularity;
    }

    public List<Map<String, Object>> getStudentGrowthData() {
        String sql = "SELECT enrollment_year, COUNT(*) as count " +
                "FROM student WHERE is_active = TRUE " +
                "GROUP BY enrollment_year ORDER BY enrollment_year";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getGradeTrendData() {
        String sql = "SELECT exam_date, AVG(score) as avg_score " +
                "FROM grade WHERE exam_type = '期末' " +
                "GROUP BY exam_date ORDER BY exam_date";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getStatisticsReports() {
        String sql = "SELECT * FROM statistics_report ORDER BY created_at DESC LIMIT 20";
        return jdbcTemplate.queryForList(sql);
    }

    public boolean generateStatisticsReport(Map<String, Object> reportConfig) {
        String sql = "INSERT INTO statistics_report (report_type, report_name, generated_by, data_json) " +
                "VALUES (?, ?, ?, ?)";
        
        try {
            jdbcTemplate.update(sql, 
                reportConfig.get("report_type"),
                reportConfig.get("report_name"),
                reportConfig.get("generated_by"),
                reportConfig.get("data_json")
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Object> getScoreChartData() {
        Map<String, Object> chartData = new HashMap<>();
        
        // 成绩分布数据
        Map<String, Integer> scoreDistribution = getScoreDistribution();
        chartData.put("scoreDistribution", scoreDistribution);
        
        // 课程平均分
        String avgSql = "SELECT c.course_name, AVG(g.score) as avg_score " +
                "FROM course c LEFT JOIN grade g ON c.id = g.course_id " +
                "GROUP BY c.id, c.course_name HAVING COUNT(g.id) > 0";
        List<Map<String, Object>> courseAverages = jdbcTemplate.queryForList(avgSql);
        chartData.put("courseAverages", courseAverages);
        
        return chartData;
    }

    public Map<String, Object> getCourseChartData() {
        Map<String, Object> chartData = new HashMap<>();
        chartData.put("coursePopularity", getCoursePopularity());
        return chartData;
    }
}