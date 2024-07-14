package com.blog.cesmusic.data.DTO.v1.auth;

import java.time.Instant;

public class TokenDTO {

    private String login;
    private String token;
    private Instant created;
    private Instant expiration;

    public TokenDTO() {}

    public TokenDTO(String login, String token, Instant created, Instant expiration) {
        this.login = login;
        this.token = token;
        this.created = created;
        this.expiration = expiration;
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

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }
}
