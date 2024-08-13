package com.blog.cesmusic.services.auth;

import com.blog.cesmusic.data.DTO.v1.auth.LoginCodeDTO;
import com.blog.cesmusic.data.DTO.v1.auth.UserDTO;
import com.blog.cesmusic.exceptions.general.ResourceNotFoundException;
import com.blog.cesmusic.mapper.Mapper;
import com.blog.cesmusic.model.LoginCode;
import com.blog.cesmusic.repositories.LoginCodeRepository;
import com.blog.cesmusic.services.mail.MailService;
import com.blog.cesmusic.services.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class LoginCodeService {
    private final Logger logger = Logger.getLogger(LoginCodeService.class.getName());
    private final int CODE_LENGTH = 6;
    private final int VALID_MINUTES = 5;

    @Autowired
    private LoginCodeRepository repository;

    @Autowired
    private MailService mailService;

    public LoginCodeDTO findById(UUID id) {
        logger.info("Finding login code by id");

        return Mapper.parseObject(
                repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("No records found for this id")),
                LoginCodeDTO.class
        );
    }

    public List<LoginCodeDTO> findByUserLogin(String userLogin) {
        logger.info("Finding login code by user login");

        return Mapper.parseListObjects(repository.findByUserLogin(userLogin), LoginCodeDTO.class);
    }

    private LoginCodeDTO loginCodeDTOFactory(UserDTO user) {
        return new LoginCodeDTO(
                RandomStringGenerator.generateRandomString(CODE_LENGTH),
                user,
                LocalDateTime.now()
        );
    }

    public LoginCodeDTO create(UserDTO user) {
        logger.info("Creating a login code");

//        UserDTO userDTO = Mapper.parseObject(userService.findByLogin(userLogin), UserDTO.class);

        LoginCodeDTO loginCode = loginCodeDTOFactory(user);

        LoginCode entity = Mapper.parseObject(loginCode, LoginCode.class);

        LoginCodeDTO loginCodeDTO =  Mapper.parseObject(repository.save(entity), LoginCodeDTO.class);

        sendLoginCodeUserToUser(loginCodeDTO);

        return loginCodeDTO;
    }

    public boolean codeIsValid(String userLogin, String code) {
        logger.info("Validating a login code");

        List<LoginCodeDTO> userLoginCodes = findByUserLogin(userLogin);
        List<LoginCodeDTO> userActiveLoginCodes = getActiveLoginCodes(userLoginCodes);

        for (LoginCodeDTO loginCode : userActiveLoginCodes) {
            if (loginCode.getCode().equals(code)) return true;
        }

        return false;
    }

    private List<LoginCodeDTO> getActiveLoginCodes(List<LoginCodeDTO> allUserLoginCodes) {
        logger.info("Finding active login codes");

        LocalDateTime minimumTime = LocalDateTime.now().minusMinutes(VALID_MINUTES);

        return allUserLoginCodes.stream().filter(
                loginCode -> loginCode.getCreatedAt().isAfter(minimumTime)
        ).toList();
    }

    private void sendLoginCodeUserToUser(LoginCodeDTO loginCode) {
        mailService.sendLoginCodeMail(loginCode);
    }
}
