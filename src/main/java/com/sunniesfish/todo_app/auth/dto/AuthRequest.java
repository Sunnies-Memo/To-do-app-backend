package com.sunniesfish.todo_app.auth.dto;

import lombok.Data;

@Data
public class  AuthRequest {
    private String username;
    private String password;
}
