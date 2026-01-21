package com.example.Demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getCourseList() {
        String sql = "SELECT c.*, t.name as teacher_name FROM course c LEFT JOIN teacher t ON c.teacher_id = t.id";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getCourseScheduleList() {
        String sql = "SELECT cs.*, c.course_name, cls.class_name, t.name as teacher_name " + 
                    "FROM course_schedule cs " +
                    "LEFT JOIN course c ON cs.course_id = c.id " +
                    "LEFT JOIN class cls ON cs.class_id = cls.id " +
                    "LEFT JOIN teacher t ON cs.teacher_id = t.id " +
                    "ORDER BY cs.day_of_week, cs.start_time";
        return jdbcTemplate.queryForList(sql);
    }

    public boolean addCourseSchedule(Map<String, Object> scheduleData) {
        String sql = "INSERT INTO course_schedule (course_id, class_id, teacher_id, classroom, day_of_week, start_time, end_time, semester, academic_year) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql, scheduleData.get("course_id"), scheduleData.get("class_id"),
                    scheduleData.get("teacher_id"), scheduleData.get("classroom"), scheduleData.get("day_of_week"),
                    scheduleData.get("start_time"), scheduleData.get("end_time"), scheduleData.get("semester"),
                    scheduleData.get("academic_year"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}