package com.sunniesfish.todo_app.auth.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String profileImg;
}
