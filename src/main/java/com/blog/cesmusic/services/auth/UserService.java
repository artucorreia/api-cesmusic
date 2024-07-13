package com.blog.cesmusic.services.auth;

import com.blog.cesmusic.data.DTO.v1.auth.RegisterDTO;
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

    public UserDetails register(RegisterDTO data) {
        String passwordEncoder = new BCryptPasswordEncoder().encode(data.getPassword());
        User entity = new User(data.getLogin(), passwordEncoder, data.getRole());
        return repository.save(entity);
    }
}
