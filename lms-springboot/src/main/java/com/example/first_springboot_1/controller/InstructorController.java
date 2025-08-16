package com.example.first_springboot_1.controller;

import com.example.first_springboot_1.entities.Instructor;
import com.example.first_springboot_1.service.InstructorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {
    private final InstructorService service;
    public InstructorController(InstructorService service) { this.service = service; }

    @GetMapping public List<Instructor> list() { return service.list(); }
    @GetMapping("/{id}") public Instructor get(@PathVariable Long id) { return service.get(id); }
    @PostMapping public Instructor create(@Valid @RequestBody Instructor i) { return service.create(i); }
    @PutMapping("/{id}") public Instructor update(@PathVariable Long id, @RequestBody Instructor i) { return service.update(id, i); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.delete(id); }
}
