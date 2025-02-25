package com.codeit.team5.ican.controller.dto.auth;

import lombok.Getter;

@Getter
public class CodeitLoginResponse {
    private CodeitUserResponse user;
    private String refreshToken;
    private String accessToken;
}
