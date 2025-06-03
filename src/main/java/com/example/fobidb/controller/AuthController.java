package com.example.fobidb.controller;

import com.example.fobidb.dto.LoginRequest;
import com.example.fobidb.repository.TeacherRepository;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.hamcrest.core.IsNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.fobidb.service.AuthService;
//import com.example.fobidb.repository.AuthRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * * @Author: Michel P.
 * * @Date: 28.05.2025
 * * @Description: Klasse für die Authentifizierung.
 * <p>
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;

    /*@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        boolean isAuthenticated = authService.authenticateUser(loginRequest);

    }*/
}
