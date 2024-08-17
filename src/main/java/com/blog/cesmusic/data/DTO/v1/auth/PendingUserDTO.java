package com.blog.cesmusic.data.DTO.v1.auth;

import java.time.LocalDateTime;
import java.util.UUID;

public class PendingUserDTO {

    private UUID id;
    private String fullName;
    private String login;
    private String password;
    private String loginCode;
    private LocalDateTime createdAt;

    public PendingUserDTO() {}

    public PendingUserDTO(UUID id, String fullName, String login, String password, String loginCode, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.login = login;
        this.password = password;
        this.loginCode = loginCode;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}