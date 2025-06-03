package com.example.fobidb.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


import java.util.List;

/**
 * * @Author: Michel P.
 * * @Date: 02.06.2025
 * * @Description: Klasse für LoginRequests.
 * <p>
 */

@Data
public class LoginRequest {
    private String email;
    private String password;
}
