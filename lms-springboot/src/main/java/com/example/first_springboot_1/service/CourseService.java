package com.example.first_springboot_1.service;

import com.example.first_springboot_1.dao.CourseRepository;
import com.example.first_springboot_1.dao.InstructorRepository;
import com.example.first_springboot_1.entities.Course;
import com.example.first_springboot_1.entities.Instructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final ActivityLogService activity;

    public CourseService(CourseRepository courseRepository, InstructorRepository instructorRepository, ActivityLogService activity) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.activity = activity;
    }

    public List<Course> list() { return courseRepository.findAll(); }

    public Course get(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new LmsNotFoundException("Course "+id+" not found"));
    }

    @Transactional
    public Course create(Course c) {
        Long instId = c.getInstructor() != null ? c.getInstructor().getId() : null;
        if (instId == null) throw new IllegalArgumentException("Instructor is required");
        Instructor inst = instructorRepository.findById(instId)
                .orElseThrow(() -> new LmsNotFoundException("Instructor "+instId+" not found"));
        c.setInstructor(inst);
        Course saved = courseRepository.save(c);
        activity.log("system","CREATE_COURSE","courseId="+saved.getId(),"INFO");
        return saved;
    }

    @Transactional
    public Course update(Long id, Course patch) {
        Course c = get(id);
        if (patch.getTitle() != null) c.setTitle(patch.getTitle());
        if (patch.getDescription() != null) c.setDescription(patch.getDescription());
        if (patch.getInstructor() != null && patch.getInstructor().getId() != null) {
            Instructor inst = instructorRepository.findById(patch.getInstructor().getId())
                    .orElseThrow(() -> new LmsNotFoundException("Instructor "+patch.getInstructor().getId()+" not found"));
            c.setInstructor(inst);
        }
        return courseRepository.save(c);
    }

    @Transactional
    public void delete(Long id) {
        courseRepository.delete(get(id));
        activity.log("system","DELETE_COURSE","courseId="+id,"WARN");
    }
}
