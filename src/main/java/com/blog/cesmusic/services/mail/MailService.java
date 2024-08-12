package com.blog.cesmusic.services.mail;

import com.blog.cesmusic.data.DTO.v1.auth.UserDTO;
import com.blog.cesmusic.exceptions.mail.MailSendingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@Service
public class MailService {
    private final Logger logger = Logger.getLogger(MailService.class.getName());

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendNewUserMail(UserDTO newUser, String adminLogin) {
        logger.info("Sending mail to admin");

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("<" + adminLogin + ">");
            helper.setFrom("no-reply@cesmusic.blog");
            helper.setSubject("New User");

            String template = getMailTemplate("templates/new-user-mail-template.html");
            template = template.replace("${name}", newUser.getFullName());
            template = template.replace("${login}", newUser.getLogin());

            helper.setText(
                    "New user:\n" +
                            "\nName: " + newUser.getFullName() +
                            "\nLogin: " + newUser.getLogin(),
                    template
            );

            javaMailSender.send(message);
        }
        catch (Exception e) {
            throw new MailSendingException("Error when trying to send mail");
        }
    }

    public void sendUserAcceptedMail(UserDTO user) {
        logger.info("Sending mail to user");

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("<" + user.getLogin() + ">");
            helper.setFrom("no-reply@cesmusic.blog");
            helper.setSubject("Conta Ativada");

            String template = getMailTemplate("templates/user-accept-mail-template.html");
            template = template.replace("${name}", user.getFullName());

            helper.setText(
                    "Sua conta foi ativada com sucesso",
                    template
            );

            javaMailSender.send(message);
        }
        catch (Exception e) {
            throw new MailSendingException("Error when trying to send mail");
        }
    }

    private String getMailTemplate(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}