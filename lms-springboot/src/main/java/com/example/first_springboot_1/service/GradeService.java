package com.example.first_springboot_1.service;

import com.example.first_springboot_1.dao.EnrollmentRepository;
import com.example.first_springboot_1.dao.GradeRepository;
import com.example.first_springboot_1.entities.Enrollment;
import com.example.first_springboot_1.entities.Grade;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GradeService {
    private final GradeRepository gradeRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ActivityLogService activity;

    public GradeService(GradeRepository gradeRepository, EnrollmentRepository enrollmentRepository, ActivityLogService activity) {
        this.gradeRepository = gradeRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.activity = activity;
    }

    @Transactional
    @CacheEvict(value = "studentReports", key = "#studentId")
    public Grade recordGrade(Long enrollmentId, int score, Long studentId, String actor) {
        Enrollment e = enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new LmsNotFoundException("Enrollment "+enrollmentId+" not found"));
        Grade g = new Grade();
        g.setEnrollment(e);
        g.setScore(score);
        Grade saved = gradeRepository.save(g);
        activity.log(actor,"GRADE","enrollmentId="+enrollmentId+", score="+score,"INFO");
        return saved;
    }
}
