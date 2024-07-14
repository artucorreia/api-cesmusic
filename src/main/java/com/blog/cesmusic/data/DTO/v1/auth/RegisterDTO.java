package com.blog.cesmusic.data.DTO.v1.auth;

import com.blog.cesmusic.model.Role;

public class RegisterDTO {

    private String fullName;

    private String login;

    private String password;

    private Role role;

    public RegisterDTO(String fullName, String login, String password, Role role) {
        this.fullName = fullName;
        this.login = login;
        this.password = password;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}