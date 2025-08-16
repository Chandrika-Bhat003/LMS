package com.example.first_springboot_1.service;

import com.example.first_springboot_1.dao.StudentRepository;
import com.example.first_springboot_1.entities.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repo;

    // Constructor injection (recommended in Spring)
    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    // Fetch all students
    public List<Student> list() {
        return repo.findAll();
    }

    // Fetch student by ID
    public Student get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new LmsNotFoundException("Student with ID " + id + " not found"));
    }

    // Create a new student
    public Student create(Student student) {
        return repo.save(student);
    }

    // Update student details (partial update supported)
    public Student update(Long id, Student patch) {
        Student student = get(id); // fetch existing student

        if (patch.getName() != null && !patch.getName().isBlank()) {
            student.setName(patch.getName());
        }
        if (patch.getEmail() != null && !patch.getEmail().isBlank()) {
            student.setEmail(patch.getEmail());
        }

        return repo.save(student); // save updated entity
    }

    // Delete student by ID
    public void delete(Long id) {
        Student student = get(id); // check existence
        repo.delete(student);
    }
}
