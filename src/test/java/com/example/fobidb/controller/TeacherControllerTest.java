/*
 *   * Author: Chris M.
 *   * @Date: 28.05.2025
 *   *
 *   * @Description: Testklasse für den TeacherController.
 *   *
 *   * @Last Update: 28.05.25, 11:24
 *   * @Reason:
 *
 *
 */

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
    Teacher testTeacher = Teacher
            .builder()
            .name("Max")
            .lastName("Mustermann")
            .shortName("mm")
            .email("maxmustermann@mail.to")
            .trainingTime(20)
            .build();


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
    void getTeachers() throws Exception {
        when(teacherService.getAllTeachers())
                .thenReturn(List.of(testTeacher));

        mockMvc.perform(get("/teacher"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Max"));
    }

    // GetTeacherByMail Test
    @Test
    void getTeacherByMail() throws Exception {
        when(teacherService.getTeacherByMail(testTeacher.getEmail()))
                .thenReturn(java.util.Optional.of(testTeacher));

        mockMvc.perform(get("/teacher/email")
                        .param("email", testTeacher.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("Max"));
    }

    @Test
    void createTeacher() throws Exception {
        String json = objectMapper.writeValueAsString(testTeacher);

        mockMvc.perform(post("/teacher")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Max"));


    }

    @Test
    void deleteTeacher() throws Exception {
        testTeacher.setId(1L);

        long id = testTeacher.getId();

        when(teacherRepository.findTeacherById(id)).thenReturn(java.util.Optional.of(testTeacher));
        when(teacherService.getAllTeachers()).thenReturn(List.of(testTeacher));

        String json = objectMapper.writeValueAsString(id);

        mockMvc.perform(delete("/teacher")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void updateTeacher() throws Exception {
        testTeacher.setId(1L);

        when(teacherRepository.findTeacherById(testTeacher.getId()))
                .thenReturn(java.util.Optional.of(testTeacher));

        when(teacherService.getAllTeachers()).thenReturn(List.of(testTeacher))
                .thenReturn(List.of(testTeacher));

        testTeacher.setName("Maxi");

        String json = objectMapper.writeValueAsString(testTeacher);

        mockMvc.perform(put("/teacher")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Maxi"));
    }
}