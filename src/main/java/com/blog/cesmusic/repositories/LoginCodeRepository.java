package com.blog.cesmusic.repositories;

import com.blog.cesmusic.model.LoginCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoginCodeRepository extends JpaRepository<LoginCode, UUID> {
    List<LoginCode> findByUserLogin(String userLogin);
}
