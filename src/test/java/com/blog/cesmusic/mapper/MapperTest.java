package com.blog.cesmusic.mapper;

import com.blog.cesmusic.data.DTO.v1.auth.UserDTO;
import com.blog.cesmusic.model.Role;
import com.blog.cesmusic.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MapperTest {

    @Test
    @DisplayName("Should map User object to UserDTO")
    void parseObjectUser() {
        User user = new User(
                UUID.randomUUID(),
                "João Carlos",
                "joaocarlos@gmail.com",
                "password",
                Role.USER,
                "About me",
                LocalDateTime.now(),
                true
        );

        UserDTO userDTO = Mapper.parseObject(user, UserDTO.class);

        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getFullName(), userDTO.getFullName());
        assertEquals(user.getLogin(), userDTO.getLogin());
        assertEquals(user.getRole(), userDTO.getRole());
        assertEquals(user.getAbout(), userDTO.getAbout());
        assertEquals(user.getCreatedAt(), userDTO.getCreatedAt());
        assertEquals(user.getActive(), userDTO.getActive());
    }

    @Test
    @DisplayName("Should map list of User objects to list of UserDTO")
    void parseListUserObjects() {
        List<User> users = new ArrayList<>();
        users.add(new User(
                UUID.randomUUID(),
                "user 1",
                "user1@gmail.com",
                "password",
                Role.USER,
                "About user 1",
                LocalDateTime.now(),
                true
        ));
        users.add(new User(
                UUID.randomUUID(),
                "user 2",
                "user2@gmail.com",
                "password",
                Role.USER,
                "About user 2",
                LocalDateTime.now(),
                false
        ));
        users.add(new User(
                UUID.randomUUID(),
                "admin 1",
                "admin1@gmail.com",
                "password",
                Role.ADMIN,
                "About admin 1",
                LocalDateTime.now(),
                true
        ));
        users.add(new User(
                UUID.randomUUID(),
                "admin 2",
                "admin2@gmail.com",
                "password",
                Role.ADMIN,
                "About admin 2",
                LocalDateTime.now(),
                true
        ));

        List<UserDTO> userDTOs = Mapper.parseListObjects(users, UserDTO.class);

        assertEquals(users.size(), userDTOs.size());
        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i).getId(), userDTOs.get(i).getId());
            assertEquals(users.get(i).getFullName(), userDTOs.get(i).getFullName());
            assertEquals(users.get(i).getLogin(), userDTOs.get(i).getLogin());
            assertEquals(users.get(i).getRole(), userDTOs.get(i).getRole());
            assertEquals(users.get(i).getAbout(), userDTOs.get(i).getAbout());
            assertEquals(users.get(i).getCreatedAt(), userDTOs.get(i).getCreatedAt());
            assertEquals(users.get(i).getActive(), userDTOs.get(i).getActive());
        }
    }
}