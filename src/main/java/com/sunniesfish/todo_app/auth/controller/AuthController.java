package com.sunniesfish.todo_app.auth.controller;

import com.sunniesfish.todo_app.auth.util.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("")
    public ResponseEntity login(String username, String password) {
        return null;
    }

    @PostMapping("/token")
    public ResponseEntity refreshToken() {
        return null;
    }

}
