package com.example.fobidb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * * @Author: Michel P.
 * * @Date: 28.05.2025
 * * @Description: Konfiguration für den PasswordEncoder.
 * <p>
 */

@Configuration
public class AuthConfig {

    /**
     * Definiert einen BCryptPasswordEncoder als Spring Bean.
     * Dieser Bean kann dann in anderen Komponenten (z.B. AuthService) injiziert werden.
     *
     * @return Eine Instanz von BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


