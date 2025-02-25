package com.codeit.team5.ican.controller.dto.auth;

import lombok.Getter;

@Getter
public class CodeitTokenRefreshResponse {
    private String accessToken;
    private String refreshToken;
}
