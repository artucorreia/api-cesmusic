package com.blog.cesmusic.data.DTO.v1.auth;

import com.blog.cesmusic.model.Role;

import java.util.Set;
import java.util.UUID;

public class UserResponseDTO {
    private UUID id;
    private String fullName;
    private String login;
    private Role role;
    private Boolean enabled;
    private Set<String> authorities;

    public UserResponseDTO() {}

    public UserResponseDTO(UUID id, String fullName, String login, Role role, Boolean enabled, Set<String> authorities) {
        this.id = id;
        this.fullName = fullName;
        this.login = login;
        this.role = role;
        this.enabled = enabled;
        this.authorities = authorities;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
}
