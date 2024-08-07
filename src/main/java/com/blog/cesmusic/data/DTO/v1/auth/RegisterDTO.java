package com.blog.cesmusic.data.DTO.v1.auth;

public class RegisterDTO {

    private String fullName;
    private String login;
    private String password;
    private String about;

    public RegisterDTO(String fullName, String login, String password, String about) {
        this.fullName = fullName;
        this.login = login;
        this.password = password;
        this.about = about;
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}