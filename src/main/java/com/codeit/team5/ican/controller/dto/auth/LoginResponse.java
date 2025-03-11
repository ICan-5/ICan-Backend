package com.codeit.team5.ican.controller.dto.auth;

import com.codeit.team5.ican.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private UserResponseWithProfile user;
    private String refreshToken;
    private String accessToken;

    public static LoginResponse of(CodeitLoginResponse response, User user) {
        return LoginResponse.builder()
                .user(UserResponseWithProfile.of(response, user))
                .refreshToken(response.getRefreshToken())
                .accessToken(response.getAccessToken())
                .build();
    }

}
