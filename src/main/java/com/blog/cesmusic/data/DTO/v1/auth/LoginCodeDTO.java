package com.blog.cesmusic.data.DTO.v1.auth;

import java.time.LocalDateTime;
import java.util.UUID;

public class LoginCodeDTO {
    private UUID id;
    private String code;
    private UserDTO user;
    private LocalDateTime createdAt;

    public LoginCodeDTO() {}

    public LoginCodeDTO(String code, UserDTO user, LocalDateTime createdAt) {
        this.code = code;
        this.user = user;
        this.createdAt = createdAt;
    }

    public LoginCodeDTO(UUID id, String code, UserDTO user, LocalDateTime createdAt) {
        this.id = id;
        this.code = code;
        this.user = user;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
