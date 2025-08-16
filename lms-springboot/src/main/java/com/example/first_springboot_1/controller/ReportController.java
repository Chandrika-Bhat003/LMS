package com.example.first_springboot_1.controller;

import com.example.first_springboot_1.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService service;
    public ReportController(ReportService service) { this.service = service; }

    @GetMapping("/student/{studentId}")
    public Map<String, Object> studentReport(@PathVariable Long studentId) {
        return service.studentProgress(studentId);
    }
}
