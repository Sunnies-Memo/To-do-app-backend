package com.sunniesfish.todo_app.global.util;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final String ACCESS_TOKEN_SECRET = "accessSecretKey";
    private final String REFRESH_TOKEN_SECRET = "refreshSecretKey";
    private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 60; // 1시간
    private final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7일

    public String generateAccessToken(String username) {
        return null;
    }

    public String generateRefreshToken(String username) {
        return null;
    }

    public String getUsernameFromAccessToken(String token) {
        return null;
    }

    //Access Token 유효성 검증
    public boolean validateAccessToken(String token) {
        return false;
    }
    
    //Refresh Token 유효성 검증
    public boolean validateRefreshToken(String token) {
        return false;
    }

    private boolean validateToken(String token, String key) {
        return false;
    }
}
