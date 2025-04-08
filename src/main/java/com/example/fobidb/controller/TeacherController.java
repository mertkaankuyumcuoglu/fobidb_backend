package com.example.fobidb.controller;

import com.example.fobidb.entity.Teacher;
import com.example.fobidb.repository.TeacherRepository;
import com.example.fobidb.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<List<Teacher>> getTeachers() {
        List<Teacher> teacher = teacherService.getAllTeachers();
        return ResponseEntity.ok(teacher);
    }

    @GetMapping("/email")
    public ResponseEntity<Optional<Teacher>> getTeacherByEmail(@RequestParam("email") String email) {
        Optional<Teacher> teacher = teacherService.getTeacherByEmail(email);
        return ResponseEntity.ok(teacher);
    }

    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        System.out.println(teacher);
        teacherService.addTeacher(teacher);
        return ResponseEntity.ok(teacher);
    }

    @DeleteMapping
    public ResponseEntity<Teacher> deleteTeacher(@RequestBody Teacher teacher) {
        teacherService.deleteTeacher(teacher);
        return ResponseEntity.ok(teacher);
    }

    @PutMapping
    public ResponseEntity<Teacher> updateTeacher(@RequestBody Teacher teacher) {
        teacherService.updateTeacher(teacher);
        return ResponseEntity.ok(teacher);
    }
}
