package com.blog.cesmusic.repositories;

import com.blog.cesmusic.data.DTO.v1.auth.UserDTO;
import com.blog.cesmusic.model.Role;
import com.blog.cesmusic.model.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should get inactive users from DB")
    void findInactiveUsers() {
        createUser(new UserDTO(UUID.randomUUID(),"Active User", "active@gmail.com", Role.USER, "About active", LocalDateTime.now(), true));
        createUser(new UserDTO(UUID.randomUUID(),"Inactive User 1", "inactive1@gmail.com", Role.USER, "About inactive 1", LocalDateTime.now(), false));
        createUser(new UserDTO(UUID.randomUUID(),"Inactive User 2", "inactive2@gmail.com", Role.USER, "About inactive 2", LocalDateTime.now(), false));

        List<User> result = userRepository.findInactiveUsers();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(User::getLogin).containsExactlyInAnyOrder("inactive1@gmail.com", "inactive2@gmail.com");
        assertThat(result).extracting(User::getActive).containsOnly(false);
    }

    @Sql(scripts = "/db/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    @DisplayName("Should get administrator's emails")
    void findAdminsLogin() {
        createUser(new UserDTO(UUID.randomUUID(),"Active User 1", "active1@gmail.com", Role.USER, "About active", LocalDateTime.now(), true));
        createUser(new UserDTO(UUID.randomUUID(),"Active User 2", "active2@gmail.com", Role.USER, "About active", LocalDateTime.now(), true));
        createUser(new UserDTO(UUID.randomUUID(),"Active User 3", "active3@gmail.com", Role.USER, "About active", LocalDateTime.now(), true));
        createUser(new UserDTO(UUID.randomUUID(),"Admin 1", "admin1@gmail.com", Role.ADMIN, "About admin 1", LocalDateTime.now(), true));
        createUser(new UserDTO(UUID.randomUUID(),"Admin 2", "admin2@gmail.com", Role.ADMIN, "About admin 2", LocalDateTime.now(), true));

        List<String> result = userRepository.findAdminsLogin();

        assertThat(result)
                .hasSize(2)
                .containsExactlyInAnyOrder("admin1@gmail.com", "admin2@gmail.com")
                .doesNotContain("active1@gmail.com", "active2@gmail.com", "active3@gmail.com");
    }

    private void createUser(UserDTO data) {
        User user = new User(
                null,
                data.getFullName(),
                data.getLogin(),
                "12345678",
                data.getRole(),
                data.getAbout(),
                data.getCreatedAt(),
                data.getActive()
        );

        entityManager.persist(user);
    }
}