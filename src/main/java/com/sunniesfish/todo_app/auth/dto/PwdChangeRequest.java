package com.sunniesfish.todo_app.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePwdRequest {

    private String username;
    private String oldPassword;
    private String newPassword;
}
