package com.example.first_springboot_1.service;

import com.example.first_springboot_1.dao.EnrollmentRepository;
import com.example.first_springboot_1.dao.GradeRepository;
import com.example.first_springboot_1.entities.Enrollment;
import com.example.first_springboot_1.entities.Grade;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

@Service
public class ReportService {
    private final EnrollmentRepository enrollmentRepository;
    private final GradeRepository gradeRepository;

    public ReportService(EnrollmentRepository enrollmentRepository, GradeRepository gradeRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.gradeRepository = gradeRepository;
    }

    @Cacheable(value = "studentReports", key = "#studentId")
    public Map<String, Object> studentProgress(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        List<Grade> grades = gradeRepository.findByEnrollmentStudentId(studentId);
        long completed = grades.stream().map(g -> g.getEnrollment().getCourse().getId()).distinct().count();
        OptionalDouble avg = grades.stream().mapToInt(Grade::getScore).average();
        Map<String,Object> report = new HashMap<>();
        report.put("studentId", studentId);
        report.put("totalCourses", enrollments.size());
        report.put("completedCourses", completed);
        report.put("averageScore", avg.isPresent() ? avg.getAsDouble() : null);
        return report;
    }
}
