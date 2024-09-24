package com.sunniesfish.todo_app.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String ACCESS_TOKEN_SECRET;
    private final String REFRESH_TOKEN_SECRET;

    public JwtUtil(
            @Value("${jwt.secret.access}") String accessTokenSecret,
            @Value("${jwt.secret.refresh}") String refreshTokenSecret
    ) {
        this.ACCESS_TOKEN_SECRET = accessTokenSecret;
        this.REFRESH_TOKEN_SECRET = refreshTokenSecret;
    }

    private SecretKey generateSecretKey(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String username) {
        // 1시간
        long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 60;
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(generateSecretKey(ACCESS_TOKEN_SECRET), SignatureAlgorithm.HS512)
                .compact();
        return token;
    }

    public String generateRefreshToken(String username) {
        // 7일
        long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7;
        Map<String , Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(generateSecretKey(REFRESH_TOKEN_SECRET))
                .compact();
    }

    public String extractUsername(String token, String key) {
        return extractClaim(token, Claims::getSubject, key);
    }

    public String getUsernameFormAccessToken(String token) {
        return extractUsername(token, ACCESS_TOKEN_SECRET);
    }
    public String getUsernameFormRefreshToken(String token) {
        return extractUsername(token, REFRESH_TOKEN_SECRET);
    }

    public Date extractExpiration(String token, String key) {
        return extractClaim(token, Claims::getExpiration, key);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, String key) {
        final Claims claims = extractAllClaims(token, key);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, String key) {
        return Jwts.parserBuilder()
                .setSigningKey(generateSecretKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token, String key) {
        return extractExpiration(token, key).before(new Date());
    }

    //Access Token 유효성 검증
    public boolean validateAccessToken(String token, String username) {
        return validateToken(token, ACCESS_TOKEN_SECRET, username);
    }
    
    //Refresh Token 유효성 검증
    public boolean validateRefreshToken(String token, String username) {
        return validateToken(token, REFRESH_TOKEN_SECRET, username);
    }

    private boolean validateToken(String token, String key, String username) {

        final String tokenUsername = extractUsername(token, key);
        return (tokenUsername.equals(username) && !isTokenExpired(token, key));

    }
}
