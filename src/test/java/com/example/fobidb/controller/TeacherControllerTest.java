package com.example.fobidb.controller;

import com.example.fobidb.entity.Teacher;
import com.example.fobidb.repository.TeacherRepository;
import com.example.fobidb.service.TeacherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {
    // Test Lehrer
    Teacher testTeacher = new Teacher("Max", "Mustermann", "mm", "maxmustermann@mail.to", 20);
    ObjectMapper objectMapper = new ObjectMapper();

    // Initialisiert den MockMvc
    @Autowired
    private MockMvc mockMvc;

    // Initialisiert den TeacherService
    @MockBean
    private TeacherService teacherService;

    // Initialisiert das TeacherRepository
    @MockBean
    private TeacherRepository teacherRepository;

    // GetTeachers Test
    @Test
    void GetTeachers() throws Exception {
        when(teacherService.GetAllTeachers())
                .thenReturn(List.of(testTeacher));

        mockMvc.perform(get("/api/v1/teacher"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Max"));
    }

    // GetTeacherByMail Test
    @Test
    void GetTeacherByMail() throws Exception {
        when(teacherService.GetTeacherByMail(testTeacher.getEmail()))
                .thenReturn(java.util.Optional.of(testTeacher));

        mockMvc.perform(get("/api/v1/teacher/email")
                .param("email", testTeacher.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("Max"));
    }

    @Test
    void CreateTeacher() throws Exception {
        String json = objectMapper.writeValueAsString(testTeacher);

        mockMvc.perform(post("/api/v1/teacher")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Max"));


    }

    @Test
    void DeleteTeacher() throws Exception {
        testTeacher.setId(1L);

        long id = testTeacher.getId();

        when(teacherRepository.findTeacherById(id)).thenReturn(java.util.Optional.of(testTeacher));
        when(teacherService.GetAllTeachers()).thenReturn(List.of(testTeacher));

        String json = objectMapper.writeValueAsString(id);

        mockMvc.perform(delete("/api/v1/teacher")
                .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void UpdateTeacher() {
    }
}