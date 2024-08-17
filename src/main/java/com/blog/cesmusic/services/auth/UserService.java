package com.blog.cesmusic.services.auth;

import com.blog.cesmusic.data.DTO.v1.auth.*;
import com.blog.cesmusic.exceptions.auth.*;
import com.blog.cesmusic.mapper.Mapper;
import com.blog.cesmusic.model.Role;
import com.blog.cesmusic.model.User;
import com.blog.cesmusic.repositories.UserRepository;
import com.blog.cesmusic.services.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserService {
    private final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository repository;

    @Autowired
    private MailService mailService;

    public UserDetails findByLogin(String login) {
        logger.info("Finding user by login");

        return repository.findByLogin(login);
    }

    public void checkUserStatus(AuthenticationDTO data) {
        logger.info("Checking if user is inactive");

        User user = Mapper.parseObject(findByLogin(data.getLogin()), User.class);
        if (!user.getActive()) throw new InactiveUserException("Inactive user");
    }

    public UserDTO register(PendingUserDTO data) {
        logger.info("Registering a new user");

        User entity = userFactory(data);

        sendMailToAdmins(Mapper.parseObject(entity, UserDTO.class));

        return create(entity);
    }

    private UserDTO create(User entity) {
        logger.info("Creating a new user");

        return Mapper.parseObject(
                repository.save(entity),
                UserDTO.class
        );
    }

    private User userFactory(PendingUserDTO data) {
        return new User(
                data.getFullName().toUpperCase().trim(),
                data.getLogin(),
                data.getPassword(),
                Role.USER,
                "",
                data.getCreatedAt(),
                false
        );
    }

    public UserDTO acceptUser(String login) {
        logger.info("Accepting a user");

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
        logger.info("Finding inactive users");

        return Mapper.parseListObjects(
                repository.findInactiveUsers(),
                UserDTO.class
        );
    }

    private void sendMailToAdmins(UserDTO user) {
        for (String login : repository.findAdminsLogin()) {
            mailService.sendNotificationOfNewRegistrationToAdministrator(user, login);
        }
    }

    private void sendMailToUser(UserDTO user) {
        mailService.sendAcceptedUserNotification(user);
    }
}