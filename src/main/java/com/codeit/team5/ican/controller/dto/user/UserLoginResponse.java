package com.codeit.team5.ican.controller.dto.user;

import lombok.Getter;

@Getter
public class UserLoginResponse {
    private UserRegisterResponse user;
    private String refreshToken;
    private String accessToken;
}
