package com.example.fobidb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * * @Author: Michel P.
 * * @Date: 03.06.2025
 * * @Description: Klasse für Registrierungsanfragen.
 * <p>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Nachname darf nicht leer sein")
    private String lastName;

    @NotBlank(message = "Vorname darf nicht leer sein")
    private String firstName;

    @NotBlank(message = "Kürzel darf nicht leer sein")
    private String shortName;

    @NotBlank(message = "E-Mail darf nicht leer sein")
    @Email(message = "Kein gültiges E-Mail-Format")
    private String email;

    @NotBlank(message = "Passwort darf nicht leer sein")
    @Size(min = 8,message = "Passwort muss mindestens 8 Zeichen lang sein")
    private String password;
}
