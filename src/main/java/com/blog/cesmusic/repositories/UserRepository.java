package com.blog.cesmusic.repositories;

import com.blog.cesmusic.model.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    UserDetails findByLogin(String login);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM USERS U WHERE U.`ROLE` = 'PENDING'"
    )
    List<User> findUsersPending();
}