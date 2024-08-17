package com.blog.cesmusic.data.DTO.v1.auth;

import java.time.Instant;

public class TokenDTO {

    private String login;
    private String token;
    private Instant createdAt;
    private Instant expiresIn;

    public TokenDTO() {}

    public TokenDTO(String login, String token, Instant createdAt, Instant expiresIn) {
        this.login = login;
        this.token = token;
        this.createdAt = createdAt;
        this.expiresIn = expiresIn;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Instant expiresIn) {
        this.expiresIn = expiresIn;
    }
}
