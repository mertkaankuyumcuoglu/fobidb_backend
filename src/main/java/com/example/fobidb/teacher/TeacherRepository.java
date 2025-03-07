package com.example.fobidb.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // findTeacherByEmail ist eine SQL Abfrage @Query("SELECT s FROM Teacher s WHERE s.email = ?1")
    Optional<Teacher> findTeacherById(Long id);
    Optional<Teacher> findTeacherByEmail(String email);
    Optional<Teacher> findTeacherBySurname(String surname);
    Optional<Teacher> findTeacherByName(String name);
    Optional<Teacher> findTeacherByNameshort(String nameshort);
}
