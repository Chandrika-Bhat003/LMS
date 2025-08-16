package com.example.first_springboot_1.service;

import com.example.first_springboot_1.dao.*;
import com.example.first_springboot_1.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnrollmentService {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentService.class);
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ActivityLogService activity;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository,
                             CourseRepository courseRepository, ActivityLogService activity) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.activity = activity;
    }

    public List<Enrollment> list() { return enrollmentRepository.findAll(); }

    @Transactional
    public Enrollment enroll(Long studentId, Long courseId, String actor) {
        Student s = studentRepository.findById(studentId).orElseThrow(() -> new LmsNotFoundException("Student "+studentId+" not found"));
        Course c = courseRepository.findById(courseId).orElseThrow(() -> new LmsNotFoundException("Course "+courseId+" not found"));
        enrollmentRepository.findByStudentAndCourse(s, c).ifPresent(e -> { throw new IllegalStateException("Already enrolled"); });
        Enrollment e = new Enrollment();
        e.setStudent(s); e.setCourse(c);
        Enrollment saved = enrollmentRepository.save(e);
        activity.log(actor,"ENROLL","studentId="+studentId+", courseId="+courseId,"INFO");
        logger.debug("Enrolled student {} to course {}", studentId, courseId);
        return saved;
    }

    public List<Enrollment> byStudent(Long studentId) { return enrollmentRepository.findByStudentId(studentId); }
    public List<Enrollment> byCourse(Long courseId) { return enrollmentRepository.findByCourseId(courseId); }
}
