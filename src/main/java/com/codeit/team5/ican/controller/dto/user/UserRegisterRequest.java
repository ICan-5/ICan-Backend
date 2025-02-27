package com.codeit.team5.ican.controller.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserRegisterRequest {
    @NotNull
    private String email;
    @NotNull
    private String name;
    @NotNull
    private String password;
}
