package com.blog.cesmusic.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "login_codes")
public class LoginCode {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 6)
    private String code;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public LoginCode() {}

    public LoginCode(UUID id, String code, LocalDateTime createdAt, User user) {
        this.id = id;
        this.code = code;
        this.createdAt = createdAt;
        this.user = user;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginCode loginCode = (LoginCode) o;
        return Objects.equals(id, loginCode.id)
                && Objects.equals(code, loginCode.code)
                && Objects.equals(createdAt, loginCode.createdAt)
                && Objects.equals(user, loginCode.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, createdAt, user);
    }
}
