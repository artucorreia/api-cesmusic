package com.blog.cesmusic.services.auth;

import com.blog.cesmusic.data.DTO.v1.auth.*;
import com.blog.cesmusic.exceptions.auth.*;
import com.blog.cesmusic.exceptions.general.ResourceNotFoundException;
import com.blog.cesmusic.mapper.Mapper;
import com.blog.cesmusic.model.PendingUser;
import com.blog.cesmusic.repositories.PendingUserRepository;
import com.blog.cesmusic.services.mail.MailService;
import com.blog.cesmusic.services.mail.MailValidatorService;
import com.blog.cesmusic.services.util.LoginCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class PendingUserService {
    private final Logger logger = Logger.getLogger(PendingUserService.class.getName());

    @Autowired
    private PendingUserRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;


    public PendingUserDTO findById(UUID id) {
        logger.info("Finding pending user by id");

        return Mapper.parseObject(
                repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("No records found for this id")),
                PendingUserDTO.class
        );
    }

    public void delete(UUID id) {
        logger.info("Deleting a pending user by id");

        repository.delete(
                Mapper.parseObject(
                        findById(id),
                        PendingUser.class
                )
        );
    }

    private Optional<PendingUser> findByLogin(String login) {
        logger.info("Finding pending user by login");

        return  repository.findByLogin(login);
    }

    public PendingUserDTO register(RegisterDTO data) {
        logger.info("Registering a new pending user");

        validateData(data);

        PendingUser entity = pendingUserFactory(data);

        sendLoginCodeToUser(Mapper.parseObject(entity, PendingUserDTO.class));

        return Mapper.parseObject(create(entity), PendingUserDTO.class);
    }

    private PendingUserDTO findByLoginCode(String code) {
        logger.info("Finding pending user by code");

        return Mapper.parseObject(
                repository.findByLoginCode(code)
                        .orElseThrow(
                                () -> new InvalidLoginCodeException("Invalid or expired validation code")
                        ),
                PendingUserDTO.class
        );
    }

    public UserDTO validateLoginCode(String code) {
        logger.info("Validating code");

        PendingUserDTO pendingUser = findByLoginCode(code);

        delete(pendingUser.getId());

        return userService.register(pendingUser);
    }

    private PendingUser pendingUserFactory(RegisterDTO data) {
        String passwordEncoder = new BCryptPasswordEncoder().encode(data.getPassword());
        return new PendingUser(
                data.getFullName().toUpperCase().trim(),
                data.getLogin(),
                passwordEncoder,
                LoginCodeGenerator.generateCode(),
                LocalDateTime.now()
        );
    }

    private PendingUserDTO create(PendingUser entity) {
        logger.info("Creating a new pending user");

        return Mapper.parseObject(
                repository.save(entity),
                PendingUserDTO.class
        );
    }

    private void sendLoginCodeToUser(PendingUserDTO pendingUser) {
        mailService.sendEmailValidationCode(pendingUser);
    }

    private void validateData(RegisterDTO data) {
        logger.info("Validating registration data");

        if (!MailValidatorService.isValid(data.getLogin())) throw new InvalidEmailException("Invalid email format");
        if (!dataLengthIsValid(data.getLogin()   , 10))  throw new DataLengthException("Email must have 10 characters or more");
        if (!dataLengthIsValid(data.getPassword(), 8))  throw new DataLengthException("Password must have 8 or more characters");
        if (!dataLengthIsValid(data.getFullName(), 5))  throw new DataLengthException("Name must have 5 or more characters");

        if (findByLogin(data.getLogin()).isPresent()) throw new PendingUserAlreadyRegisteredException("A validation code has already been sent for this login");
        if (userService.findByLogin(data.getLogin()) != null) throw new LoginAlreadyUsedException("This email is already in use");
    }

    private Boolean dataLengthIsValid(String data, int length) {
        return data.length() >= length;
    }
}