package com.blog.cesmusic.data.DTO.v1.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterDTO {

    @NotBlank
    @Size(min = 5, max = 100, message = "Name must have 5 characters or more")
    private String fullName;

    @Email(message = "Invalid email format")
    private String login;

    @NotBlank
    @Size(min = 8, max = 50, message = "Password must have 8 characters or more")
    private String password;

    public RegisterDTO(String fullName, String login, String password) {
        this.fullName = fullName;
        this.login = login;
        this.password = password;
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
}