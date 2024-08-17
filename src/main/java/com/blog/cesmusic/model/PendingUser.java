package com.blog.cesmusic.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "pending_users")
public class PendingUser {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    @Column(length = 100, unique = true, nullable = false)
    private String login;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(name = "login_code",  length = 32, nullable = false)
    private String loginCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public PendingUser() {}

    public PendingUser(String fullName, String login, String password, String loginCode, LocalDateTime createdAt) {
        this.fullName = fullName;
        this.login = login;
        this.password = password;
        this.loginCode = loginCode;
        this.createdAt = createdAt;
    }

    public PendingUser(UUID id, String fullName, String login, String password, String loginCode, LocalDateTime createdAt) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PendingUser that = (PendingUser) o;
        return Objects.equals(id, that.id)
                && Objects.equals(fullName, that.fullName)
                && Objects.equals(login, that.login)
                && Objects.equals(password, that.password)
                && Objects.equals(loginCode, that.loginCode)
                && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, login, password, loginCode, createdAt);
    }
}
