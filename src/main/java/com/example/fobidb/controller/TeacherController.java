package com.example.fobidb.controller;

import com.example.fobidb.dto.TeacherResponse;
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
 * * @Author: Chris M.
 * * @Author: Mert Kaan K.
 * * @Author: Michel P.
 * * @Date: 07.04.2025
 * <p>
 * * @Description: Diese Klasse verwaltet die HTTP-Anfragen für die Lehrer-Entität.
 * <p>
 * * @Last Update: 08.04.2025
 * * @Last Update by: Chris M.
 * * @Reason: Kommentar hinzugefügt
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
    public ResponseEntity<TeacherResponse> createTeacher(@RequestBody Teacher teacher) {
        try {
            teacherService.addTeacher(teacher);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            TeacherResponse teacherResponse = new TeacherResponse();
            teacherResponse.setErrorMessage(e.getMessage());
            return ResponseEntity.ok(teacherResponse);
        }
    }

    // Löscht einen Lehrer
    @DeleteMapping
    public ResponseEntity<String> deleteTeacher(@RequestBody Long id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findTeacherById(id);

        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            teacherService.deleteTeacher(teacher);
            return ResponseEntity.ok(null);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Aktualisiert einen Lehrer
    @PutMapping
    public ResponseEntity<Teacher> updateTeacher(@RequestBody Teacher teacher) {
        teacherService.updateTeacher(teacher);
        return ResponseEntity.ok(null);
    }
}
