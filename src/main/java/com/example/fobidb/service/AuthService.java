package com.example.fobidb.service;

import com.example.fobidb.dto.LoginRequest;
import com.example.fobidb.dto.RegisterRequest;
import com.example.fobidb.entity.Teacher;
import com.example.fobidb.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Die AuthService-Klasse für die Authentifizierungslogik (Registrierung, Login, Passwort ändern).
 * Hier findet die eigentliche Geschäftslogik statt, z.B. Passwort-Hashing und Datenbank-Interaktion.
 *
 * @Author: Michel P.
 * @Date: 03.06.2025
 */

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Registriert einen neuen Lehrer
    @Transactional
    public Teacher registerTeacher(RegisterRequest registerRequest) {

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

    // user login
    @Transactional
    // bisher ohne Rückgabewert -- soll ein Token zurückgeben
    public void userLogin(LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String rawPassword = loginRequest.getPassword();

        Optional<Teacher> optionalTeacher = teacherRepository.findTeacherByEmail(email);

        if(optionalTeacher.isEmpty()) {
            throw new RuntimeException("E-Mail oder Passwort ungültig");
        }

        Teacher teacher = optionalTeacher.get();

        if(passwordEncoder.matches(rawPassword, teacher.getPasswordHash())) {
            // Login wäre hier erfolgreich -- print ist nur Platzhalter
            System.out.println("Login erfolgreich");
        }
        else {
            throw new RuntimeException("E-Mail oder Passwort ungültig");
            // lieber BadCredentialsException verwenden?
        }
    }
}