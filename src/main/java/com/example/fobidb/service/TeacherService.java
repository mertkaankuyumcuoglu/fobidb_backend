package com.example.fobidb.service;

import com.example.fobidb.entity.Teacher;
import com.example.fobidb.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public void updateTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public void addTeacher(Teacher teacher) {
        Teacher teacherToAdd = new Teacher();
        teacherToAdd.setName(teacher.getName());
        teacherToAdd.setLastName(teacher.getLastName());
        teacherToAdd.setEmail(teacher.getEmail());
        teacherToAdd.setShortName(teacher.getShortName());
        teacherToAdd.setTrainingTime(teacher.getTrainingTime());
        teacherRepository.save(teacherToAdd);
    }

    public void deleteTeacher(Teacher teacher) {
        teacherRepository.delete(teacher);
    }

    public Optional<Teacher> getTeacherById(long id) {
        return teacherRepository.findTeacherById(id);
    }

    public Optional<Teacher> getTeacherByEmail(String email) {
        return teacherRepository.findTeacherByEmail(email);
    }
}
