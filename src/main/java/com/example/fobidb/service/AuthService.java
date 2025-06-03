package com.example.fobidb.service;

import com.example.fobidb.dto.RegisterRequest;
import com.example.fobidb.entity.Teacher;
import com.example.fobidb.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Die AuthService-Klasse für die Authentifizierungslogik (Registrierung, Login, Passwort ändern).
 * Hier findet die eigentliche Geschäftslogik statt, z.B. Passwort-Hashing und Datenbank-Interaktion.
 *
 * @Author: Michel P.
 * @Date: 03.06.2025
 */

@Service
@RequiredArgsConstructor
@Configuration
public class AuthService {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    // erstellt einen neuen Lehrer
    @Transactional
    public Teacher createTeacher(RegisterRequest registerRequest) {

        // Prüft ob bereits ein Lehrer anhand der Email existiert
        if(teacherRepository.existsTeacherByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Diese E-Mail-Adresse ist bereits vergeben");
        }

        // Passwort hashen
        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());

        // Erstellt ein neues Objekt des Lehrers
        Teacher newTeacher = new Teacher();
        newTeacher.setFirstName(registerRequest.getFirstName());
        newTeacher.setLastName(registerRequest.getLastName());
        newTeacher.setEmail(registerRequest.getEmail());
        newTeacher.setShortName(registerRequest.getShortName());
        newTeacher.setPasswordHash(hashedPassword);

        // Überträgt das Lehrerobjekt an die Datenbank
        return teacherRepository.save(newTeacher);
    }


}
