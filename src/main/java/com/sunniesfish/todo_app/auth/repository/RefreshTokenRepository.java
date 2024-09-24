package com.sunniesfish.todo_app.auth.repository;

import com.sunniesfish.todo_app.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember_Username(String username);
    void deleteByMember_Username(String username);
}
