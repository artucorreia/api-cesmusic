package com.blog.cesmusic.repositories;

import com.blog.cesmusic.data.DTO.v1.auth.PendingUserDTO;
import com.blog.cesmusic.model.PendingUser;
import com.blog.cesmusic.services.util.LoginCodeGenerator;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PendingUserRepositoryTest {

    @Autowired
    private PendingUserRepository pendingUserRepository;

    @Autowired
    private EntityManager entityManager;

    private final LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(10);

    @Test
    @DisplayName("Should delete pending user with expired login code from DB")
    void deleteAllByCreatedAtBefore() {
        createPendingUser(new PendingUserDTO(null,"pending user1", "pending1@gmail.com", "12345678", LoginCodeGenerator.generateCode(), LocalDateTime.now().minusDays(20)));
        createPendingUser(new PendingUserDTO(null,"pending user2", "pending2@gmail.com", "12345678", LoginCodeGenerator.generateCode(), LocalDateTime.now().minusMinutes(10)));
        createPendingUser(new PendingUserDTO(null,"pending user3", "pending3@gmail.com", "12345678", LoginCodeGenerator.generateCode(), LocalDateTime.now().minusMinutes(2)));
        createPendingUser(new PendingUserDTO(null,"pending user4", "pending4@gmail.com", "12345678", LoginCodeGenerator.generateCode(), LocalDateTime.now().minusMinutes(11)));

        pendingUserRepository.deleteAllByCreatedAtBefore(expirationTime);

        List<PendingUser> result = pendingUserRepository.findAll();

        assertThat(result)
                .hasSize(2)
                .extracting(PendingUser::getLogin)
                .containsExactlyInAnyOrder("pending2@gmail.com", "pending3@gmail.com");
    }

    void createPendingUser(PendingUserDTO data) {
        PendingUser entity = new PendingUser(
                null,
                data.getFullName(),
                data.getLogin(),
                data.getPassword(),
                data.getLoginCode(),
                data.getCreatedAt()
        );

        entityManager.persist(entity);
    }
}