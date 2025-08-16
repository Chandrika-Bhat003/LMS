package com.example.first_springboot_1.controller;

import com.example.first_springboot_1.entities.Student;
import com.example.first_springboot_1.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    // ✅ Get all students
    @GetMapping
    public ResponseEntity<List<Student>> list() {
        return ResponseEntity.ok(service.list());
    }

    // ✅ Get student by id
    @GetMapping("/{id}")
    public ResponseEntity<Student> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    // ✅ Create student
    @PostMapping
    public ResponseEntity<Student> create(@Valid @RequestBody Student s) {
        Student created = service.create(s);
        return ResponseEntity.ok(created);
    }

    // ✅ Update student
    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable Long id, @Valid @RequestBody Student s) {
        Student updated = service.update(id, s);
        return ResponseEntity.ok(updated);
    }

    // ✅ Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
