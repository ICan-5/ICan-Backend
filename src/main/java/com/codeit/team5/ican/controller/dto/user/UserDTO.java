package com.codeit.team5.ican.controller.dto.user;

import com.codeit.team5.ican.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;

    private Long codeitId;

    private String email;

    private String name;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private String profile;

    public static UserDTO from(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .codeitId(user.getCodeitId())
                .email(user.getEmail())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .profile(user.getProfile())
                .build();
    }

}
