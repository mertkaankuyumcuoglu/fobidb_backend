package com.example.fobidb.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    // findTeacherByEmail ist eine SQL Abfrage (SELECT....)
    Optional<Teacher> findTeacherByEmail(String email);
}
