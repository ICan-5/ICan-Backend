package com.codeit.team5.ican.controller.dto;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String email;
    private String name;
    private String password;
}
