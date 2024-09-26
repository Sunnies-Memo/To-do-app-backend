package com.sunniesfish.todo_app.auth.service;

import com.sunniesfish.todo_app.auth.dto.AuthResponse;
import com.sunniesfish.todo_app.auth.dto.RegisterRequest;
import com.sunniesfish.todo_app.auth.entity.Member;
import com.sunniesfish.todo_app.auth.entity.RefreshToken;
import com.sunniesfish.todo_app.auth.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private MemberCRUDService memberCRUDService;
    private RefreshTokenService refreshTokenService;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    @Transactional
    public AuthResponse getTokens(UserDetails userDetails) {
        Optional<Member> memberOpt = memberCRUDService.findByUsername(userDetails.getUsername());
        return memberCRUDService.findByUsername(userDetails.getUsername())
                .map(member -> {
                    String accessToken = jwtUtil.generateAccessToken(userDetails.getUsername());
                    //기존 RefreshToken이 존재하고 유효한지 확인
                    Optional<RefreshToken> existingRefreshToken = refreshTokenService.FindRefreshTokenByUsername(userDetails.getUsername());
                    if (existingRefreshToken.isPresent() &&
                            jwtUtil.validateRefreshToken(existingRefreshToken.get().getRefreshToken(), userDetails.getUsername())) {
                        String refreshToken = existingRefreshToken.get().getRefreshToken();
                        return new AuthResponse(member,accessToken, refreshToken); // 새 RefreshToken 발급 필요 없음
                    }

                    //기존 RefreshToken 삭제 후 새로 발급
                    refreshTokenService.deleteRefreshToken(userDetails.getUsername());
                    String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
                    refreshTokenService.saveRefreshToken(
                            RefreshToken.builder()
                                    .refreshToken(refreshToken)
                                    .member(member)
                                    .build()
                    );

                    // AccessToken과 새 RefreshToken 반환
                    return new AuthResponse(member,accessToken, refreshToken);
                })
                // 사용자 존재하지 않을 경우 예외 처리
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    @Transactional
    public Member register(RegisterRequest registerRequest) {
        Optional<Member> memberOptional = memberCRUDService.findByUsername(registerRequest.getUsername());
        if (memberOptional.isPresent()) {
            return null;
        } else {
            String password = passwordEncoder.encode(registerRequest.getPassword());
            return memberCRUDService.create(
                    Member.builder()
                            .username(registerRequest.getUsername())
                            .password(password)
                            .build()
            );
        }
    }

    @Transactional
    public void logout(String token) {
        refreshTokenService.deleteRefreshToken(token);
    }
}
