package com.blog.cesmusic.services.auth;

import com.blog.cesmusic.data.DTO.v1.auth.RegisterDTO;
import com.blog.cesmusic.data.DTO.v1.auth.UserResponseDTO;
import com.blog.cesmusic.exceptions.auth.FullNameLengthException;
import com.blog.cesmusic.exceptions.auth.FullNameNullException;
import com.blog.cesmusic.exceptions.auth.LoginLengthException;
import com.blog.cesmusic.exceptions.auth.PasswordLengthException;
import com.blog.cesmusic.mapper.Mapper;
import com.blog.cesmusic.model.User;
import com.blog.cesmusic.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserDetails findByLogin(String login) {
        return repository.findByLogin(login);
    }

    public UserResponseDTO register(RegisterDTO data) {
        if (!loginLengthIsValid(data.getLogin())) throw new LoginLengthException("Login must be 6 characters or more");
        if (!passwordLengthIsValid(data.getPassword())) throw new PasswordLengthException("Password must be 8 or more characters");
        if (!fullNameNonNull(data.getFullName())) throw new FullNameNullException("Name can not be null");
        if (!fullNameLengthIsValid(data.getFullName())) throw new FullNameLengthException("Name must be 5 or more characters");

        String passwordEncoder = new BCryptPasswordEncoder().encode(data.getPassword());
        String name = capitalizeName(data.getFullName());

        return Mapper.parseObject(
                repository.save(
                        new User(name, data.getLogin(), passwordEncoder, data.getRole())
                ),
                UserResponseDTO.class
        );
    }

    private Boolean loginLengthIsValid(String login) {
        return login.length() >= 6;
    }

    private Boolean fullNameNonNull(String name) {return name != null;}

    private Boolean fullNameLengthIsValid(String name) {
        return name.length() >= 5;
    }

    private Boolean passwordLengthIsValid(String password) {
        return password.length() >= 8;
    }

    private String capitalizeName(String name) {
        return name.toUpperCase().trim();
    }
}
