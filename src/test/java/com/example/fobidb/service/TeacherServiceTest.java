package com.example.fobidb.service;

import com.example.fobidb.entity.Teacher;
import com.example.fobidb.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 ** Author: Chris M.
 ** @Date: 09.04.2025
 **
 ** @Description: Testklasse für den TeacherService.
 **
 ** @Last Update: 09.04.2025
 ** @Reason: Erstellung der Testklasse für den TeacherService.
 */

@Slf4j
class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    public TeacherServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    Teacher testTeacher = new Teacher("Max", "Mustermann", "mm", "maxmustermann@mail.to", 20);

    @Test
    void getAllTeachers() {
        // Lehrer hinzufügen
        Teacher teacher1 = new Teacher("John", "Lastname", "jl", "johnlastname@mail.to", 10);
        Teacher teacher2 = testTeacher;

        // Wenn teacherRepository.findAll() aufgerufen wird, gib die Liste der Lehrer zurück
        when(teacherRepository.findAll()).thenReturn(List.of(teacher1, teacher2));

        // Spiele den Aufruf der Methode GetAllTeachers() vor
        List<Teacher> teachers = teacherService.GetAllTeachers();

        // Überprüfe, ob die zurückgegebene Liste die erwarteten Lehrer enthält
        assertEquals(2, teachers.size());
        assertEquals(teachers.get(0).getName(), teacher1.getName());
        assertEquals(teachers.get(1).getName(), teacher2.getName());
    }

    @Test
    void updateTeacher() {
        // Lehrer hinzufügen
        Teacher teacherToEdit = testTeacher;
        Teacher editedTeacher = new Teacher();

        // Wenn teacherRepository.save() aufgerufen wird, gib den bearbeiteten Lehrer zurück
        when(teacherRepository.save(teacherToEdit)).thenReturn(teacherToEdit);

        // Daten ändern
        teacherToEdit.setName("Maximilian");

        // Spiele den Aufruf der Methode UpdateTeacher() vor
        teacherService.UpdateTeacher(teacherToEdit);

        // Überprüfe, ob der Lehrer erfolgreich aktualisiert wurde
        assertEquals("Maximilian", teacherToEdit.getName());
    }

    @Test
    void addTeacher() {
        Teacher teacher = testTeacher;

        // Wenn teacherRepository.save() aufgerufen wird, gib den Lehrer zurück
        when(teacherRepository.save(teacher)).thenReturn(teacher);

        // Spiele den Aufruf der Methode AddTeacher() vor
        teacherService.AddTeacher(teacher);

        // Überprüfe, ob der Lehrer erfolgreich hinzugefügt wurde
        assertEquals("Max", teacher.getName());
    }

    @Test
    void deleteTeacher() {
        Teacher teacherToDelete = testTeacher;

        // Wenn teacherRepository.delete() aufgerufen wird, gib den Lehrer zurück
        when(teacherRepository.findTeacherById(teacherToDelete.getId())).thenReturn(java.util.Optional.of(teacherToDelete));

        // Spiele den Aufruf der Methode DeleteTeacher() vor
        teacherService.DeleteTeacher(teacherToDelete);

        verify(teacherRepository, times(1)).delete(teacherToDelete);
    }

    @Test
    void getTeacherById() {
        // Lehrer hinzufügen
        Teacher teacher = testTeacher;
        teacher.setId(1L);

        // Wenn teacherRepository.findTeacherById() aufgerufen wird, gib den Lehrer zurück
        when(teacherRepository.findTeacherById(teacher.getId())).thenReturn(java.util.Optional.of(teacher));

        // Spiele den Aufruf der Methode GetTeacherById() vor
        var result = teacherService.GetTeacherById(teacher.getId());

        // Überprüfe, ob der Lehrer erfolgreich abgerufen wurde
        assertTrue(result.isPresent());
        assertEquals(teacher.getName(), result.get().getName());
    }

    @Test
    void getTeacherByMail() {
        // Lehrer hinzufügen
        Teacher teacher = testTeacher;

        // Wenn teacherRepository.findTeacherByEmail() aufgerufen wird, gib den Lehrer zurück
        when(teacherRepository.findTeacherByEmail(teacher.getEmail())).thenReturn(java.util.Optional.of(teacher));

        // Spiele den Aufruf der Methode GetTeacherByMail() vor
        var result = teacherService.GetTeacherByMail(teacher.getEmail());

        // Überprüfe, ob der Lehrer erfolgreich abgerufen wurde
        assertTrue(result.isPresent());
        assertEquals(teacher.getEmail(), result.get().getEmail());
    }
}