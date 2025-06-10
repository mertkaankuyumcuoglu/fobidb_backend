package com.example.fobidb.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.fobidb.entity.Teacher;
import com.example.fobidb.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

/**
 * * @Author: Michel P.
 * * @Date: 05.06.2025
 * * @Description: In dieser Klasse wird das JSON Web Token erstellt und verifiziert.
 * <p>
 */

@Service
@RequiredArgsConstructor
public class JwtService {
    /**
     * Das Secret wird zu Dev-Zwecken vorerst hardgecodet.
     * Bei realem Einsatz der Anwendung, wird DRINGEND EMPFOHLEN dieses neu zu generieren und in eine Umgebungsvariable auszulagern.
     * Das Secret wird mit HMAC SHA-256 generiert. Empfohlen wird es mit Gemini oder ChatGPT generieren zu lassen.
     * Fügen Sie dazu einfach folgenden Text in der AI Ihrer Wahl ein: "Bitte erstelle mir Secret für JWT mit HS256".
     */
    private static final String secret = "NzUzZWE4ZDAyZWIyY2I3NjYxZTE3YzYxZWRjZDA2NGQwMTc3Y2UzMTZiMDYwNjYxZjgwNjEwOWM2Mzg5Njc5Mg==";

    private static final Algorithm algorithm = Algorithm.HMAC256(secret);

    // JWT-Verifier für die Token-Validierung
    private static final JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer("fobidb-backend")
            .withAudience("fobidb-frontend")
            .build();

    private final TeacherRepository teacherRepository;

    public String createToken(String email) {
        try {

            Optional<Teacher> teacherOptional = teacherRepository.findTeacherByEmail(email);

            if (teacherOptional.isEmpty()) {
                throw new JWTCreationException("Lehrer mit E-Mail '" + email + "' nicht gefunden. "
                        + "Token-Erstellung fehlgeschlagen.",
                        new RuntimeException("Teacher not found"));
            }

            Teacher teacher = teacherOptional.get();

            Date expiresAt = Date.from(Instant.now().plus(30, ChronoUnit.DAYS));
            Date issuedAt = Date.from(Instant.now());

            return JWT.create()
                    .withIssuer("fobidb-backend")
                    .withAudience("fobidb-frontend")
                    .withSubject(teacher.getId().toString())
                    .withClaim("lastName", teacher.getLastName())
                    .withClaim("firstName", teacher.getFirstName())
                    .withClaim("shortName", teacher.getShortName())
                    .withClaim("email", teacher.getEmail())
                    .withClaim("trainingTime", teacher.getTrainingTime())
                    .withIssuedAt(issuedAt)
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new JWTCreationException(ex.getMessage(), ex);
        }
    }

    /**
     * Extrahiert die E-Mail-Adresse aus einem JWT-Token.
     *
     * @param token Das JWT-Token als String
     * @return Die E-Mail-Adresse des Benutzers oder null, wenn das Token ungültig ist
     */
    public String extractEmail(String token) {
        try {
            // Token validieren und decodieren
            DecodedJWT decodedJWT = verifier.verify(token);

            // E-Mail aus den Claims extrahieren
            return decodedJWT.getClaim("email").asString();

        } catch (JWTVerificationException e) {
            // Bei ungültigem Token oder fehlenden Claims gibt null zurück
            return null;
        }
    }

    /**
     * Überprüft, ob ein Token gültig ist.
     *
     * @param token Das zu prüfende JWT-Token
     * @return true wenn das Token gültig ist, sonst false
     */
    public boolean validateToken(String token) {
        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}

