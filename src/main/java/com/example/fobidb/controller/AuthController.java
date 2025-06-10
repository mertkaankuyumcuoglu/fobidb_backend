package com.example.fobidb.controller;

import com.example.fobidb.dto.LoginRequest;
import com.example.fobidb.dto.LoginResponse;
import com.example.fobidb.dto.RegisterRequest;
import com.example.fobidb.entity.Teacher;
import com.example.fobidb.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/register")
    public Teacher register(@RequestBody RegisterRequest registerRequest) {
        return authService.registerTeacher(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
