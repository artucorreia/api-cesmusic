package com.blog.cesmusic.services.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.blog.cesmusic.data.DTO.v1.auth.TokenDTO;
import com.blog.cesmusic.exceptions.auth.JwtCreationTokenException;
import com.blog.cesmusic.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.logging.Logger;

@Service
public class TokenService {
    private final Logger logger = Logger.getLogger(TokenService.class.getName());

    @Value("${security.jwt.token.secret}")
    private String secretKey;

    public TokenDTO generateToken(User user) {
        logger.info("Generating token");

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            Instant createdAt = getIssueDate();
            Instant expiresIn = generateExpirationDate(createdAt);
            String token = JWT.create()
                    .withIssuer("cesmusic")
                    .withSubject(user.getLogin())
                    .withIssuedAt(createdAt)
                    .withExpiresAt(expiresIn)
                    .sign(algorithm);

            return new TokenDTO(user.getLogin(), token, createdAt, expiresIn);
        }
        catch (JWTCreationException e) {
            throw new JwtCreationTokenException("Error while generating a token");
        }
    }

    public String validateToken(String token) {
        logger.info("Validating token");

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer("cesmusic")
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch (JWTVerificationException e) {
            logger.warning("Token validation failed: " + e.getMessage());
            return "";
        }
    }

    private Instant generateExpirationDate(Instant createdAt) {
        return createdAt.plus(Duration.ofHours(2));
    }

    private Instant getIssueDate() {
        return Instant.now();
    }
}
