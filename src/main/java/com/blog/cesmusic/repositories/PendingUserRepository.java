package com.blog.cesmusic.repositories;

import com.blog.cesmusic.model.PendingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PendingUserRepository extends JpaRepository<PendingUser, UUID> {

    Optional<PendingUser> findByLogin(String login);

    Optional<PendingUser> findByLoginCode(String code);

    @Modifying
    @Query("DELETE FROM PendingUser pu WHERE pu.createdAt < :expirationTime")
    void deleteAllByCreatedAtBefore(@Param("expirationTime") LocalDateTime expirationTime);
}
