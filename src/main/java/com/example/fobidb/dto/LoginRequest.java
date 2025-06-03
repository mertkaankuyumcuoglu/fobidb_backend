package com.example.fobidb.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

/**
 * * @Author: Michel P.
 * * @Date: 03.06.2025
 * * @Description: Klasse für LoginRequests.
 * <p>
 */

@Data
public class LoginRequest {
    @NotBlank(message = "E-Mail darf nicht leer sein")
    @Email(message = "Keine gültige E-Mail-Adresse erkannt")
    private String email;
    @NotBlank(message = "Passwort darf nicht leer sein")
    private String password;
}
