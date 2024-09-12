package com.sunniesfish.todo_app.member.controller;

import com.sunniesfish.todo_app.global.util.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity login(String username, String password) {
        return null;
    }
}
