package com.example.fobidb.controller;

import com.example.fobidb.entity.Teacher;
import com.example.fobidb.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<Optional<Teacher>> getTeacher(@RequestParam("id") Integer id) {
        Optional<Teacher> teacher = teacherService.getTeacherById(id);
        return ResponseEntity.ok(teacher);
    }

    @GetMapping("/email")
    public ResponseEntity<Optional<Teacher>> getTeacherByEmail(@RequestParam("email") String email) {
        Optional<Teacher> teacher = teacherService.getTeacherByEmail(email);
    return ResponseEntity.ok(teacher);
    }
}
