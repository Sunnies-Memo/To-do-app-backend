package com.sunniesfish.todo_app.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PwdChangeRequest {

    private String username;

    @NotBlank
    @Size(min = 7, max = 13, message = "Password must be between 7 and 13 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{7,}$",
            message = "Password must include letters, numbers, and special characters")

    private String oldPassword;

    @NotBlank
    @Size(min = 7, max = 13, message = "Password must be between 7 and 13 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{7,}$",
            message = "Password must include letters, numbers, and special characters")

    private String newPassword;
}
