package com.codeit.team5.ican.controller.dto.user;

import lombok.Getter;

@Getter
public class UserRegisterRequest {
    private String email;
    private String name;
    private String password;
}
