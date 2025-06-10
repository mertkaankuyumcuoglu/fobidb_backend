/*
 *   * Author: Chris M.
 *   * @Date:
 *   *
 *   * @Description:
 *   *
 *   * @Last Update: 28.05.25, 11:12
 *   * @Reason:
 *
 *
 */

package com.example.fobidb.service;

import com.example.fobidb.entity.Teacher;
import com.example.fobidb.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@Slf4j
class TeacherServiceTest {
    private Teacher teacher1;
    private Teacher teacher2;

    @Mock
    private TeacherRepository teacherRepository;
    @InjectMocks
    private TeacherService teacherService;

    public TeacherServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUp() {
        teacher1 = Teacher
                .builder()
                .name("John")
                .lastName("Lastname")
                .shortName("jl")
                .email("johnlastname@mail.to")
                .trainingTime(10)
                .build();


        teacher2 = Teacher
                .builder()
                .name("Max")
                .lastName("Mustermann")
                .shortName("mm")
                .email("maxmustermann@mail.to")
                .trainingTime(20)
                .build();
    }

    @Test
    void getAllTeachers() {
        // Wenn teacherRepository.findAll() aufgerufen wird, gib die Liste der Lehrer zurück
        when(teacherRepository.findAll()).thenReturn(List.of(teacher1, teacher2));

        // Spiele den Aufruf der Methode GetAllTeachers() vor
        List<Teacher> teachers = teacherService.getAllTeachers();

        // Überprüfe, ob die zurückgegebene Liste die erwarteten Lehrer enthält
        assertEquals(2, teachers.size());
        assertEquals(teachers.get(0).getFirstName(), teacher1.getFirstName());
        assertEquals(teachers.get(1).getFirstName(), teacher2.getFirstName());
    }

    @Test
    void updateTeacher() {
        // Lehrer hinzufügen
        Teacher teacherToEdit = teacher2;
        Teacher editedTeacher = new Teacher();

        // Wenn teacherRepository.save() aufgerufen wird, gib den bearbeiteten Lehrer zurück
        when(teacherRepository.save(teacherToEdit)).thenReturn(teacherToEdit);

        // Daten ändern
        teacherToEdit.setFirstName("Maximilian");

        // Spiele den Aufruf der Methode UpdateTeacher() vor
        teacherService.updateTeacher(teacherToEdit);

        // Überprüfe, ob der Lehrer erfolgreich aktualisiert wurde
        assertEquals("Maximilian", teacherToEdit.getFirstName());
    }

    @Test
    void addTeacher() {
        Teacher teacher = teacher2;

        // Wenn teacherRepository.save() aufgerufen wird, gib den Lehrer zurück
        when(teacherRepository.save(teacher)).thenReturn(teacher);

        // Spiele den Aufruf der Methode AddTeacher() vor
        teacherService.addTeacher(teacher);

        // Überprüfe, ob der Lehrer erfolgreich hinzugefügt wurde
        assertEquals("Max", teacher.getFirstName());
    }

    @Test
    void deleteTeacher() {
        Teacher teacherToDelete = teacher2;

        // Wenn teacherRepository.delete() aufgerufen wird, gib den Lehrer zurück
        when(teacherRepository.findTeacherById(teacherToDelete.getId())).thenReturn(java.util.Optional.of(teacherToDelete));

        // Spiele den Aufruf der Methode DeleteTeacher() vor
        teacherService.deleteTeacher(teacherToDelete);

        verify(teacherRepository, times(1)).delete(teacherToDelete);
    }

    @Test
    void getTeacherById() {
        // Lehrer hinzufügen
        Teacher teacher = teacher2;
        teacher.setId(1L);

        // Wenn teacherRepository.findTeacherById() aufgerufen wird, gib den Lehrer zurück
        when(teacherRepository.findTeacherById(teacher.getId())).thenReturn(java.util.Optional.of(teacher));

        // Spiele den Aufruf der Methode GetTeacherById() vor
        var result = teacherService.getTeacherById(teacher.getId());

        // Überprüfe, ob der Lehrer erfolgreich abgerufen wurde
        assertTrue(result.isPresent());
        assertEquals(teacher.getFirstName(), result.get().getFirstName());
    }

    @Test
    void getTeacherByMail() {
        // Lehrer hinzufügen
        Teacher teacher = teacher2;

        // Wenn teacherRepository.findTeacherByEmail() aufgerufen wird, gib den Lehrer zurück
        when(teacherRepository.findTeacherByEmail(teacher.getEmail())).thenReturn(java.util.Optional.of(teacher));

        // Spiele den Aufruf der Methode GetTeacherByMail() vor
        var result = teacherService.getTeacherByMail(teacher.getEmail());

        // Überprüfe, ob der Lehrer erfolgreich abgerufen wurde
        assertTrue(result.isPresent());
        assertEquals(teacher.getEmail(), result.get().getEmail());
    }
}