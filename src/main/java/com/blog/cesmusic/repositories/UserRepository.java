package com.blog.cesmusic.repositories;

import com.blog.cesmusic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    UserDetails findByLogin(String login);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM users u WHERE u.active = false"
    )
    List<User> findInactiveUsers();

    @Query(
            nativeQuery = true,
            value = "SELECT u.login FROM users u WHERE u.role = 'ADMIN'"
    )
    List<String> findAdminsLogin();
}