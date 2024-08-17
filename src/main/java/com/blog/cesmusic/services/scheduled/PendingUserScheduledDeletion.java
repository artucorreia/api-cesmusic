package com.blog.cesmusic.services.scheduled;

import com.blog.cesmusic.repositories.PendingUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class PendingUserScheduledDeletion {
    private final int EXPIRATION_TIME = 10;

    @Autowired
    private PendingUserRepository repository;

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void pendingUserDeletion() {
        LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(EXPIRATION_TIME);
        repository.deleteAllByCreatedAtBefore(expirationTime);
    }
}
