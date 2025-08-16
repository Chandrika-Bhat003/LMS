package com.example.first_springboot_1.controller;

import com.example.first_springboot_1.entities.Enrollment;
import com.example.first_springboot_1.service.EnrollmentService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService service;
    public EnrollmentController(EnrollmentService service) { this.service = service; }

    @GetMapping public List<Enrollment> list() { return service.list(); }

    @PostMapping
    public Enrollment enroll(@RequestParam Long studentId, @RequestParam Long courseId, Authentication auth) {
        String actor = auth != null ? auth.getName() : "system";
        return service.enroll(studentId, courseId, actor);
    }

    @GetMapping("/student/{studentId}")
    public List<Enrollment> byStudent(@PathVariable Long studentId) { return service.byStudent(studentId); }

    @GetMapping("/course/{courseId}")
    public List<Enrollment> byCourse(@PathVariable Long courseId) { return service.byCourse(courseId); }
}
