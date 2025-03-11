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

    public static UserResponseWithProfile of(CodeitLoginResponse response, User user) {
        return UserResponseWithProfile.builder()
                .id(response.getUser().getId())
                .email(response.getUser().getEmail())
                .name(response.getUser().getName())
                .createdAt(response.getUser().getCreatedAt())
                .updatedAt(response.getUser().getUpdatedAt())
                .profile(user.getProfile())
                .build();
    }

}
