package com.example.fobidb.repository;

import com.example.fobidb.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findTeacherById(Long id);
    Optional<Teacher> findTeacherByEmail(String email);
}
