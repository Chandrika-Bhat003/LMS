package com.example.first_springboot_1.controller;

import com.example.first_springboot_1.entities.Grade;
import com.example.first_springboot_1.service.GradeService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grades")
public class GradeController {
    private final GradeService service;
    public GradeController(GradeService service) { this.service = service; }

    @PostMapping
    public Grade grade(@RequestParam Long enrollmentId, @RequestParam int score, @RequestParam Long studentId, Authentication auth) {
        String actor = auth != null ? auth.getName() : "system";
        return service.recordGrade(enrollmentId, score, studentId, actor);
    }
}
