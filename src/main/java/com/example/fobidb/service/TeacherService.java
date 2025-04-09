package com.example.fobidb.service;

import com.example.fobidb.entity.Teacher;
import com.example.fobidb.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 ** @Author: Chris M.
 ** @Author: Mert Kaan K.
 ** @Author: Michel P.
 ** @Date: 08.04.2025
 ** @Description: Klasse für Veranstaltungen.
 *
 ** @Last Update: 08.04.2025
 ** @Last Update by: Chris M.
 */

@RequiredArgsConstructor
@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public List<Teacher> GetAllTeachers() {
        return teacherRepository.findAll();
    }

    public void UpdateTeacher(@NotNull Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public void AddTeacher(@NotNull Teacher teacher) {
        Teacher teacherToAdd = new Teacher();
        teacherToAdd.setName(teacher.getName());
        teacherToAdd.setLastName(teacher.getLastName());
        teacherToAdd.setEmail(teacher.getEmail());
        teacherToAdd.setShortName(teacher.getShortName());
        teacherToAdd.setTrainingTime(teacher.getTrainingTime());
        teacherRepository.save(teacherToAdd);
    }

    public void DeleteTeacher(@NotNull Teacher teacher) {
        teacherRepository.delete(teacher);
    }

    public Optional<Teacher> GetTeacherById(long id) {
        return teacherRepository.findTeacherById(id);
    }

    public Optional<Teacher> GetTeacherByMail(String email) {
        return teacherRepository.findTeacherByEmail(email);
    }
}
