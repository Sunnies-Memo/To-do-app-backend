package com.sunniesfish.todo_app.auth.service;

import com.sunniesfish.todo_app.auth.entity.RefreshToken;
import com.sunniesfish.todo_app.auth.repository.MemberRepository;
import com.sunniesfish.todo_app.auth.repository.RefreshTokenRepository;
import com.sunniesfish.todo_app.auth.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    private RefreshTokenRepository refreshTokenRepository;

    private MemberRepository memberRepository;

    private JwtUtil jwtUtil;

    public RefreshToken saveRefreshToken(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> FindRefreshTokenByUsername(String username) {
        return refreshTokenRepository.findByMember_Username(username);
    }

    public void deleteRefreshToken(String username) {
        refreshTokenRepository.deleteByMember_Username(username);
    }
}
