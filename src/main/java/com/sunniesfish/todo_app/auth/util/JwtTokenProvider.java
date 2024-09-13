package com.sunniesfish.todo_app.auth.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String ACCESS_TOKEN_SECRET;
    private final String REFRESH_TOKEN_SECRET;

    public JwtTokenProvider(
            @Value("${jwt.secret.access}") String accessTokenSecret,
            @Value("${jwt.secret.refresh}") String refreshTokenSecret
    ) {
        this.ACCESS_TOKEN_SECRET = accessTokenSecret;
        this.REFRESH_TOKEN_SECRET = refreshTokenSecret;
    }

    private SecretKey generateSecretKey(String secret) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateAccessToken(String username) {
        // 1시간
        long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 60;
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(generateSecretKey(ACCESS_TOKEN_SECRET), SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken(String username) {
        // 7일
        long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7;
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(generateSecretKey(REFRESH_TOKEN_SECRET))
                .compact();
    }

    public String getUsernameFromToken(String token, Boolean isAccessToken) {
        String secretKey = isAccessToken ? ACCESS_TOKEN_SECRET :REFRESH_TOKEN_SECRET;
        return Jwts.parserBuilder()
                .setSigningKey(generateSecretKey(secretKey))
                .build()
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
    }

    //Access Token 유효성 검증
    public boolean validateAccessToken(String token) {
        return validateToken(token, ACCESS_TOKEN_SECRET);
    }
    
    //Refresh Token 유효성 검증
    public boolean validateRefreshToken(String token) {
        return validateToken(token, REFRESH_TOKEN_SECRET);
    }

    private boolean validateToken(String token, String key) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(generateSecretKey(key))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException  | IllegalArgumentException e) {
            return false;
        }
    }
}
