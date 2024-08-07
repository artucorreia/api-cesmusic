package com.blog.cesmusic.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "USERS")
public class User implements UserDetails, Serializable {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "FULL_NAME", length = 100, nullable = false)
    private String fullName;

    @Column(name = "LOGIN", length = 100, nullable = false, unique = true)
    private String login;

    @Column(name = "PASSWORD", length = 100, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "ABOUT", length = 1000, nullable = false) @Lob
    private String about;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active;

    public User() {}

    public User(UUID id, String fullName, String login, String password, Role role, String about, Boolean active) {
        this.id = id;
        this.fullName = fullName;
        this.login = login;
        this.password = password;
        this.role = role;
        this.about = about;
        this.active = active;
    }

    public User(String fullName, String login, String password, Role role, String about, Boolean active) {
        this.fullName = fullName;
        this.login = login;
        this.password = password;
        this.role = role;
        this.about = about;
        this.active = active;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == Role.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(fullName, user.fullName)
                && Objects.equals(login, user.login)
                && Objects.equals(password, user.password)
                && role == user.role
                && Objects.equals(about, user.about)
                && Objects.equals(active, user.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, login, password, role, about, active);
    }
}
