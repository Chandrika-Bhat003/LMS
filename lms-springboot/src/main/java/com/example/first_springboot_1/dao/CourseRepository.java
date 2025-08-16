package com.example.first_springboot_1.dao;

import com.example.first_springboot_1.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> { }
