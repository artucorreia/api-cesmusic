package com.blog.cesmusic.model;

public enum Role {
    ADMIN("admin"),
    PENDING("pending");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
