package com.blog.cesmusic.data.DTO.v1.auth;

import com.blog.cesmusic.model.Role;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class UserDTO {
    private UUID id;
    private String fullName;
    private String login;
    private Role role;
    private String about;
    private LocalDateTime createdAt;
    private Boolean active;

    public UserDTO() {}

    public UserDTO(UUID id, String fullName, String login, Role role, String about, LocalDateTime createdAt, Boolean active) {
        this.id = id;
        this.fullName = fullName;
        this.login = login;
        this.role = role;
        this.about = about;
        this.createdAt = createdAt;
        this.active = active;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
