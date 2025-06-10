package com.example.fobidb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String id;
    private String lastName;
    private String firstName;
    private String shortName;
    private String email;
    private String token;  // JWT-Token für die Authentifizierung

    private String message;
}
