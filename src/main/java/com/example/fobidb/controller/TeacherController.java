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
@RequestMapping(path = "api/v1/teacher")
public class TeacherController {
    // Initialisiert den TeacherService
    private final TeacherService teacherService;
    private final TeacherRepository teacherRepository;

    // Gibt eine Liste aller Lehrer zurück
    @GetMapping
    public ResponseEntity<List<Teacher>> GetTeachers() {
        List<Teacher> teacher = teacherService.GetAllTeachers();
        return ResponseEntity.ok(teacher);
    }

    // Gibt einen Lehrer zurück, der mit der angegebenen ID übereinstimmt
    @GetMapping("/email")
    public ResponseEntity<Optional<Teacher>> GetTeacherByMail(@RequestParam("email") String email) {
        Optional<Teacher> teacher = teacherService.GetTeacherByMail(email);
        return ResponseEntity.ok(teacher);
    }

    // Fügt einen neuen Lehrer hinzu
    @PostMapping
    public ResponseEntity<Teacher> CreateTeacher(@RequestBody Teacher teacher) {
        System.out.println(teacher);
        teacherService.AddTeacher(teacher);
        return ResponseEntity.ok(teacher);
    }

    // Löscht einen Lehrer
    @DeleteMapping
    public ResponseEntity<Teacher> DeleteTeacher(@RequestBody Long id) {
        log.info("Deleting teacher with id: " + id);
        Optional<Teacher> optionalTeacher = teacherRepository.findTeacherById(id);

        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            teacherService.DeleteTeacher(teacher);
            return ResponseEntity.ok(teacher);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Aktualisiert einen Lehrer
    @PutMapping
    public ResponseEntity<Teacher> UpdateTeacher(@RequestBody Teacher teacher) {
        teacherService.UpdateTeacher(teacher);
        return ResponseEntity.ok(teacher);
    }
}
