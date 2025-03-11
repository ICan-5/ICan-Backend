package com.codeit.team5.ican.controller.dto.auth;

import com.codeit.team5.ican.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Builder
public class UserResponseWithProfile {
    private Long id;
    private String email;
    private String name;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private String profile;

    public static UserResponseWithProfile of(User user) {
        return UserResponseWithProfile.builder()
                .id(user.getCodeitId())
                .email(user.getEmail())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .profile(user.getProfile())
                .build();
    }

}
