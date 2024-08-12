package com.blog.cesmusic.services.auth;

import com.blog.cesmusic.data.DTO.v1.auth.AuthenticationDTO;
import com.blog.cesmusic.data.DTO.v1.auth.RegisterDTO;
import com.blog.cesmusic.data.DTO.v1.auth.UserDTO;
import com.blog.cesmusic.exceptions.auth.*;
import com.blog.cesmusic.mapper.Mapper;
import com.blog.cesmusic.model.Role;
import com.blog.cesmusic.model.User;
import com.blog.cesmusic.repositories.UserRepository;
import com.blog.cesmusic.services.MailValidatorService;
import com.blog.cesmusic.services.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private MailService mailService;

    public UserDetails findByLogin(String login) {
        return repository.findByLogin(login);
    }

    public void verifyPendingUser(AuthenticationDTO data) {
        User user = Mapper.parseObject(findByLogin(data.getLogin()), User.class);
        if (!user.getActive()) throw new PendingUserException("User is inactive");
    }

    public UserDTO register(RegisterDTO data) {
        validateData(data);

        String passwordEncoder = new BCryptPasswordEncoder().encode(data.getPassword());
        User entity = new User(
                data.getFullName().toUpperCase().trim(),
                data.getLogin(),
                passwordEncoder,
                Role.USER,
                "",
                false
        );

        sendMailToAdmins(Mapper.parseObject(entity, UserDTO.class));

        return Mapper.parseObject(
                repository.save(entity),
                UserDTO.class
        );
    }

    public UserDTO acceptUser(String login) {
        User entity = Mapper.parseObject(findByLogin(login), User.class);
        if (entity.getActive()) throw new UserIsAlreadyActiveException("User is already active");

        entity.setActive(true);

        UserDTO user = Mapper.parseObject(
                repository.save(entity),
                UserDTO.class
        );

        sendMailToUser(user);

        return user;
    }

    public List<UserDTO> findInactiveUsers() {
        return Mapper.parseListObjects(
                repository.findInactiveUsers(),
                UserDTO.class
        );
    }

    private void sendMailToAdmins(UserDTO user) {
        for (String login : repository.findAdminsLogin()) {
            mailService.sendNewUserMail(user, login);
        }
    }

    private void sendMailToUser(UserDTO user) {
        mailService.sendUserAcceptedMail(user);
    }

    private void validateData(RegisterDTO data) {
        // validate data length
        if (!dataLengthIsValid(data.getLogin()   , 6))  throw new DataLengthException("Login must be 6 characters or more");
        if (!dataLengthIsValid(data.getPassword(), 8))  throw new DataLengthException("Password must be 8 or more characters");
        if (!dataLengthIsValid(data.getFullName(), 5))  throw new DataLengthException("Name must be 5 or more characters");

        if (findByLogin(data.getLogin()) != null) throw new LoginAlreadyUsedException("Login already in use");
        if (!MailValidatorService.isValid(data.getLogin())) throw new InvalidEmailException("Login must be valid");
    }

    private Boolean dataLengthIsValid(String data, int length) {
        return data.length() >= length;
    }
}