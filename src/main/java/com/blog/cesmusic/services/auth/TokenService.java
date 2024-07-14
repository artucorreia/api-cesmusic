package com.blog.cesmusic.services.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.blog.cesmusic.data.DTO.v1.auth.TokenDTO;
import com.blog.cesmusic.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${security.jwt.token.secret}")
    private String secretKey;

    public TokenDTO generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            Instant created = getIssueDate();
            Instant expiration = generateExpirationDate();
            String token = JWT.create()
                    .withIssuer("cesmusic")
                    .withSubject(user.getLogin())
                    .withIssuedAt(created)
                    .withExpiresAt(expiration)
                    .sign(algorithm);

            return new TokenDTO(user.getLogin(), token, created, expiration);
        }
        catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating a token", e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer("cesmusic")
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch (JWTVerificationException e) {
            return "";
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant getIssueDate() {
        return LocalDateTime.now().toInstant(ZoneOffset.of("-03:00"));
    }
}
