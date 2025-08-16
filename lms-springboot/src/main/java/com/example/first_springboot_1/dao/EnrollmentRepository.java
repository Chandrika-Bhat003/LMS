package com.example.first_springboot_1.dao;

import com.example.first_springboot_1.entities.Enrollment;
import com.example.first_springboot_1.entities.Student;
import com.example.first_springboot_1.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByStudentAndCourse(Student student, Course course);
    List<Enrollment> findByStudentId(Long studentId);
    List<Enrollment> findByCourseId(Long courseId);
}
