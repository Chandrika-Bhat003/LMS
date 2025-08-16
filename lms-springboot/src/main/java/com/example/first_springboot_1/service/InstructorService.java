package com.example.first_springboot_1.service;

import com.example.first_springboot_1.dao.InstructorRepository;
import com.example.first_springboot_1.entities.Instructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {
    private final InstructorRepository repo;

    public InstructorService(InstructorRepository repo) { this.repo = repo; }

    public List<Instructor> list() { return repo.findAll(); }

    public Instructor get(Long id) { return repo.findById(id).orElseThrow(() -> new LmsNotFoundException("Instructor "+id+" not found")); }

    public Instructor create(Instructor i) { return repo.save(i); }

    public Instructor update(Long id, Instructor patch) {
        Instructor i = get(id);
        if (patch.getName() != null) i.setName(patch.getName());
        if (patch.getEmail() != null) i.setEmail(patch.getEmail());
        return repo.save(i);
    }

    public void delete(Long id) { repo.delete(get(id)); }
}
