package com.teamflow.backend.auth.dto;

import com.teamflow.backend.user.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank(message = "name is required")
        String name,

        @NotBlank(message = "email is required")
        @Email(message = "email must be valid")
        String email,

        @NotBlank(message = "password is required")
        @Size(min = 8, message = "password must be at least 8 characters")
        String password,

        @NotBlank(message = "position is required")
        String position,

        @NotBlank(message = "workspaceName is required")
        String workspaceName,

        UserRole role
) {
}