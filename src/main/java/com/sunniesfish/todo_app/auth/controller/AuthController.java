package com.sunniesfish.todo_app.auth.controller;

import com.sunniesfish.todo_app.auth.dto.AuthRequest;
import com.sunniesfish.todo_app.auth.dto.AuthResponse;
import com.sunniesfish.todo_app.auth.dto.RegisterRequest;
import com.sunniesfish.todo_app.auth.service.AuthService;
import com.sunniesfish.todo_app.auth.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

            AuthResponse tokens = authService.getTokens(userDetails);

            Cookie refreshTokenCookie = new Cookie("refresh_token", tokens.getRefreshToken());
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);

            response.addCookie(refreshTokenCookie);

            return ResponseEntity.ok(new AuthResponse(tokens.getAccessToken(),null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @PostMapping("/refresh")
    public ResponseEntity refreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    String refreshToken = cookie.getValue();

                    String username = jwtUtil.getUsernameFormRefreshToken(refreshToken);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if(jwtUtil.validateRefreshToken(refreshToken, userDetails.getUsername())) {
                        String newAccessToken = jwtUtil.generateAccessToken(userDetails.getUsername());
                        return ResponseEntity.ok(new AuthResponse(newAccessToken, null));
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest) {
        try {
            authService.register(registerRequest);
            return ResponseEntity.ok().body("Success");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity logout(HttpServletResponse response, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    String refreshToken = cookie.getValue();
                    try {
                        authService.logout(refreshToken);
                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
                    }

                    Cookie refreshTokenCookie = new Cookie("refreshToken", null);
                    refreshTokenCookie.setHttpOnly(true);
                    refreshTokenCookie.setSecure(true);
                    refreshTokenCookie.setPath("/");
                    refreshTokenCookie.setMaxAge(0); // 즉시 만료
                    response.addCookie(refreshTokenCookie);

                    return ResponseEntity.ok("Logged out successfully");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No refresh token found");
    }
}
