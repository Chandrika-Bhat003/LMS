package com.example.first_springboot_1.controller;

import com.example.first_springboot_1.entities.Course;
import com.example.first_springboot_1.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService service;
    public CourseController(CourseService service) { this.service = service; }

    @GetMapping public List<Course> list() { return service.list(); }
    @GetMapping("/{id}") public Course get(@PathVariable Long id) { return service.get(id); }
    @PostMapping public Course create(@Valid @RequestBody Course c) { return service.create(c); }
    @PutMapping("/{id}") public Course update(@PathVariable Long id, @RequestBody Course c) { return service.update(id, c); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.delete(id); }
}
