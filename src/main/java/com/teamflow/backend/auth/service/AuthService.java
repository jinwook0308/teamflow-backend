package com.teamflow.backend.auth.service;

import com.teamflow.backend.auth.dto.AuthUserResponse;
import com.teamflow.backend.auth.dto.LoginRequest;
import com.teamflow.backend.auth.dto.LoginResponse;
import com.teamflow.backend.auth.dto.SignupRequest;
import com.teamflow.backend.auth.dto.SignupResponse;
import com.teamflow.backend.user.entity.AppUser;
import com.teamflow.backend.user.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        String email = normalizeEmail(request.email());

        if (appUserRepository.existsByEmailIgnoreCase(email)) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }

        AppUser savedUser = appUserRepository.save(
                AppUser.builder()
                        .name(request.name().trim())
                        .email(email)
                        .password(passwordEncoder.encode(request.password()))
                        .role(request.role())
                        .position(request.position().trim())
                        .workspaceName(request.workspaceName().trim())
                        .build()
        );

        return new SignupResponse(
                true,
                "회원가입이 완료되었습니다.",
                AuthUserResponse.from(savedUser)
        );
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        AppUser user = appUserRepository.findByEmailIgnoreCase(normalizeEmail(request.email()))
                .orElseThrow(() -> new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        return new LoginResponse(
                true,
                "로그인에 성공했습니다.",
                "teamflow-local-" + UUID.randomUUID(),
                "Bearer",
                AuthUserResponse.from(user)
        );
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}