package com.codeit.team5.ican.controller.dto;

import lombok.Getter;

@Getter
public class LoginResponse {
    private RegisterResponse user;
    private String refreshToken;
    private String accessToken;
}
