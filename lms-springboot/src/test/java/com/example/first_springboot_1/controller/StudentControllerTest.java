package com.example.first_springboot_1.controller;

import com.example.first_springboot_1.entities.Student;
import com.example.first_springboot_1.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
@AutoConfigureMockMvc(addFilters = false) // disable Spring Security filters for MVC tests
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ Use real Student objects
    private Student student(long id, String name, String email) {
        Student s = new Student();
        s.setId(id);
        s.setName(name);
        s.setEmail(email);
        return s;
    }

    @Test
    void getAllStudents_returnsOkAndList() throws Exception {
        Student s1 = student(1L, "Alice Johnson", "alice@example.com");
        Student s2 = student(2L, "Bob Smith", "bob@example.com");
        Student s3 = student(3L, "Carol Williams", "carol@example.com");
        Student s4 = student(4L, "David Brown", "david@example.com");

        Mockito.when(studentService.list()).thenReturn(List.of(s1, s2, s3, s4));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Alice Johnson")))
                .andExpect(jsonPath("$[0].email", is("alice@example.com")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Bob Smith")))
                .andExpect(jsonPath("$[1].email", is("bob@example.com")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("Carol Williams")))
                .andExpect(jsonPath("$[2].email", is("carol@example.com")))
                .andExpect(jsonPath("$[3].id", is(4)))
                .andExpect(jsonPath("$[3].name", is("David Brown")))
                .andExpect(jsonPath("$[3].email", is("david@example.com")));
    }

    @Test
    void getStudentById_returnsOkAndBody() throws Exception {
        Student s = student(1L, "Alice Johnson", "alice@example.com");
        Mockito.when(studentService.get(1L)).thenReturn(s);

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Alice Johnson")))
                .andExpect(jsonPath("$.email", is("alice@example.com")));
    }

    @Test
    void createStudent_returnsOkAndCreated() throws Exception {
        // request JSON
        String body = objectMapper.writeValueAsString(
                Map.of("name", "Eve Adams", "email", "eve@example.com")
        );

        // service response
        Student created = student(5L, "Eve Adams", "eve@example.com");
        Mockito.when(studentService.create(any(Student.class))).thenReturn(created);

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.name", is("Eve Adams")))
                .andExpect(jsonPath("$.email", is("eve@example.com")));
    }

    @Test
    void updateStudent_returnsOkAndUpdated() throws Exception {
        String body = objectMapper.writeValueAsString(
                Map.of("name", "Alice Johnson Updated", "email", "alice.updated@example.com")
        );

        Student updated = student(1L, "Alice Johnson Updated", "alice.updated@example.com");
        Mockito.when(studentService.update(eq(1L), any(Student.class))).thenReturn(updated);

        mockMvc.perform(put("/api/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Alice Johnson Updated")))
                .andExpect(jsonPath("$.email", is("alice.updated@example.com")));
    }

    @Test
    void deleteStudent_returnsNoContent() throws Exception {
        Mockito.doNothing().when(studentService).delete(1L);

        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNoContent());
    }
}
