package com.teamflow.backend.auth.dto;

public record SignupResponse(
        boolean success,
        String message,
        AuthUserResponse user
) {
}