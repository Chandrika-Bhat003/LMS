package com.example.first_springboot_1.dao;

import com.example.first_springboot_1.entities.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByEnrollmentStudentId(Long studentId);
}
