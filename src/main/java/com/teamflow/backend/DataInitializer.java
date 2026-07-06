package com.teamflow.backend;

import com.teamflow.backend.user.entity.AppUser;
import com.teamflow.backend.user.entity.UserRole;
import com.teamflow.backend.user.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        createIfMissing("김태현", "owner@teamflow.com", UserRole.OWNER, "팀 리더", "컴퓨터공학과 개발팀");
        createIfMissing("이지은", "admin@teamflow.com", UserRole.ADMIN, "백엔드 개발자", "컴퓨터공학과 개발팀");
        createIfMissing("박민수", "member@teamflow.com", UserRole.MEMBER, "프론트엔드 개발자", "컴퓨터공학과 개발팀");
        createIfMissing("최유리", "viewer@teamflow.com", UserRole.VIEWER, "UI/UX 디자이너", "컴퓨터공학과 개발팀");
    }

    private void createIfMissing(String name, String email, UserRole role, String position, String workspaceName) {
        String normalizedEmail = email.toLowerCase(Locale.ROOT);

        if (appUserRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            return;
        }

        appUserRepository.save(
                AppUser.builder()
                        .name(name)
                        .email(normalizedEmail)
                        .password(passwordEncoder.encode("12345678"))
                        .role(role)
                        .position(position)
                        .workspaceName(workspaceName)
                        .build()
        );
    }
}