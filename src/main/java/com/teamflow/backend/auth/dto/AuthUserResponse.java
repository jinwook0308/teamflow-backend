package com.teamflow.backend.auth.dto;

import com.teamflow.backend.user.entity.AppUser;
import com.teamflow.backend.user.entity.UserRole;

public record AuthUserResponse(
        Long id,
        String name,
        String email,
        UserRole role,
        String position,
        String workspaceName
) {
    public static AuthUserResponse from(AppUser user) {
        return new AuthUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getPosition(),
                user.getWorkspaceName()
        );
    }
}