package com.teamflow.backend.auth.dto;

public record LoginResponse(
        boolean success,
        String message,
        String accessToken,
        String tokenType,
        AuthUserResponse user
) {
}