package com.example.first_springboot_1.service;

import com.example.first_springboot_1.dao.StudentRepository;
import com.example.first_springboot_1.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository repo;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setEmail("john@example.com");
    }

    @Test
    void testList() {
        when(repo.findAll()).thenReturn(Arrays.asList(student));

        List<Student> result = studentService.list();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("John Doe");
        verify(repo, times(1)).findAll();
    }

    @Test
    void testGetStudentExists() {
        when(repo.findById(1L)).thenReturn(Optional.of(student));

        Student result = studentService.get(1L);

        assertThat(result.getEmail()).isEqualTo("john@example.com");
        verify(repo, times(1)).findById(1L);
    }

    @Test
    void testGetStudentNotFound() {
        when(repo.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.get(2L))
                .isInstanceOf(LmsNotFoundException.class)
                .hasMessageContaining("Student with ID 2 not found");
    }

    @Test
    void testCreate() {
        when(repo.save(student)).thenReturn(student);

        Student created = studentService.create(student);

        assertThat(created.getName()).isEqualTo("John Doe");
        verify(repo, times(1)).save(student);
    }

    @Test
    void testUpdateStudentName() {
        Student patch = new Student();
        patch.setName("Updated Name");

        when(repo.findById(1L)).thenReturn(Optional.of(student));
        when(repo.save(any(Student.class))).thenReturn(student);

        Student updated = studentService.update(1L, patch);

        assertThat(updated.getName()).isEqualTo("Updated Name");
        verify(repo, times(1)).save(student);
    }

    @Test
    void testDelete() {
        when(repo.findById(1L)).thenReturn(Optional.of(student));

        studentService.delete(1L);

        verify(repo, times(1)).delete(student);
    }
}
