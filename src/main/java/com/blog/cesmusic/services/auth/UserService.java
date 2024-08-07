package com.blog.cesmusic.services.auth;

import com.blog.cesmusic.data.DTO.v1.auth.AuthenticationDTO;
import com.blog.cesmusic.data.DTO.v1.auth.RegisterDTO;
import com.blog.cesmusic.data.DTO.v1.auth.UserDTO;
import com.blog.cesmusic.exceptions.auth.*;
import com.blog.cesmusic.mapper.Mapper;
import com.blog.cesmusic.model.Role;
import com.blog.cesmusic.model.User;
import com.blog.cesmusic.repositories.UserRepository;
import com.blog.cesmusic.services.EmailValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserDetails findByLogin(String login) {
        return repository.findByLogin(login);
    }

    public void verifyPendingUser(AuthenticationDTO data) {
        User user = Mapper.parseObject(findByLogin(data.getLogin()), User.class);
        if (!user.getActive()) throw new PendingUserException("User is inactive");
    }

    public UserDTO register(RegisterDTO data) {
        if (findByLogin(data.getLogin()) != null) throw new LoginAlreadyUsedException("Login already in use");
        if (!EmailValidatorService.isValid(data.getLogin())) throw new InvalidEmailException("Login must be valid");
        if (!loginLengthIsValid(data.getLogin())) throw new LoginLengthException("Login must be 6 characters or more");
        if (!passwordLengthIsValid(data.getPassword())) throw new PasswordLengthException("Password must be 8 or more characters");
        if (!fullNameNonNull(data.getFullName())) throw new FullNameNullException("Name can not be null");
        if (!fullNameLengthIsValid(data.getFullName())) throw new FullNameLengthException("Name must be 5 or more characters");

        String passwordEncoder = new BCryptPasswordEncoder().encode(data.getPassword());
        String name = capitalizeName(data.getFullName());

        return Mapper.parseObject(
                repository.save(
                        new User(name, data.getLogin(), passwordEncoder, Role.USER, data.getAbout(),false)
                ),
                UserDTO.class
        );
    }

    public UserDTO acceptUser(String login) {
        User user = Mapper.parseObject(findByLogin(login), User.class);

        if (user.getActive()) throw new UserIsAlreadyActiveException("User is already active");

        user.setActive(true);

        return Mapper.parseObject(
                repository.save(user),
                UserDTO.class
        );
    }

    public void recuseUser(String login) {
        User user = Mapper.parseObject(findByLogin(login), User.class);

        if (user.getActive()) throw new UserIsAlreadyActiveException("User is already active");

        repository.delete(user);
    }

    public List<UserDTO> findInactiveUsers() {
        return Mapper.parseListObjects(
                repository.findInactiveUsers(),
                UserDTO.class
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
