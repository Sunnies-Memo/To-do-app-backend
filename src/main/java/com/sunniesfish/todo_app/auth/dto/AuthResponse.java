package com.sunniesfish.todo_app.auth.dto;

import com.sunniesfish.todo_app.auth.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthResponse {
    private Member member;
    private String accessToken;
    private String refreshToken;
}
