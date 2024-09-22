package com.sunniesfish.todo_app.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {


    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 12, message = "Username must be between 4 and 12 characters")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username must not contain special characters")
    private String username;

    @NotBlank
    @Size(min = 7, max = 13, message = "Password must be between 7 and 13 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{7,}$",
            message = "Password must include letters, numbers, and special characters")
    private String password;

    @Pattern(regexp = "^(https?:\\/\\/.*\\.(?:png|jpg|jpeg))$",
            message = "Please provide a valid image URL (jpg, jpeg, png, gif)")
    private String profileImg;
}
