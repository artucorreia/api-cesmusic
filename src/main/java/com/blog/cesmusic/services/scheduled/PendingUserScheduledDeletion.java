package com.blog.cesmusic.services.scheduled;

import com.blog.cesmusic.repositories.PendingUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Component
public class PendingUserScheduledDeletion {
    private final Logger logger = Logger.getLogger(PendingUserScheduledDeletion.class.getName());

    private final int EXPIRATION_TIME = 10;

    @Autowired
    private PendingUserRepository repository;

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void pendingUserDeletion() {
        logger.info("Deleting pending users");

        LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(EXPIRATION_TIME);
        repository.deleteAllByCreatedAtBefore(expirationTime);
    }
}
