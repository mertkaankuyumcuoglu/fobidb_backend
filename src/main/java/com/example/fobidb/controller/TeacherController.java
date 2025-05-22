package com.example.fobidb.controller;

import com.example.fobidb.entity.Teacher;
import com.example.fobidb.repository.TeacherRepository;
import com.example.fobidb.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 ** @Author: Chris M.
 ** @Author: Mert Kaan K.
 ** @Author: Michel P.
 ** @Date: 07.04.2025
 *
 ** @Description: Diese Klasse verwaltet die HTTP-Anfragen für die Lehrer-Entität.
 *
 ** @Last Update: 08.04.2025
 ** @Last Update by: Chris M.
 ** @Reason: Kommentar hinzugefügt
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/teacher")
public class TeacherController {
    // Initialisiert den TeacherService
    private final TeacherService teacherService;
    private final TeacherRepository teacherRepository;

    // Gibt eine Liste aller Lehrer zurück
    @GetMapping
    public ResponseEntity<List<Teacher>> getTeachers() {
        List<Teacher> teacher = teacherService.getAllTeachers();
        return ResponseEntity.ok(teacher);
    }

    // Gibt einen Lehrer zurück, der mit der angegebenen ID übereinstimmt
    @GetMapping("/email")
    public ResponseEntity<Optional<Teacher>> getTeacherByMail(@RequestParam("email") String email) {
        Optional<Teacher> teacher = teacherService.getTeacherByMail(email);
        return ResponseEntity.ok(teacher);
    }

    // Fügt einen neuen Lehrer hinzu
    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        teacherService.addTeacher(teacher);
        return ResponseEntity.ok(teacher);
    }

    // Löscht einen Lehrer
    @DeleteMapping
    public ResponseEntity<Teacher> deleteTeacher(@RequestBody Long id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findTeacherById(id);

        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            teacherService.deleteTeacher(teacher);
            return ResponseEntity.ok(teacher);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Aktualisiert einen Lehrer
    @PutMapping
    public ResponseEntity<Teacher> updateTeacher(@RequestBody Teacher teacher) {
        teacherService.updateTeacher(teacher);
        return ResponseEntity.ok(teacher);
    }
}
