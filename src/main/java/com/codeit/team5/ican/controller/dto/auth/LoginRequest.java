package com.codeit.team5.ican.controller.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
