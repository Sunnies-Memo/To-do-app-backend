package com.sunniesfish.todo_app.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
}
