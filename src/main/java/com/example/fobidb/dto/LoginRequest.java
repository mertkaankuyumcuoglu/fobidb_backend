package com.example.fobidb.dto;

import lombok.Data;

/**
 * * @Author: Michel P.
 * * @Date: 03.06.2025
 * * @Description: Klasse für LoginRequests.
 * <p>
 */

@Data
public class LoginRequest {
    private String email;

    private String password;
}
