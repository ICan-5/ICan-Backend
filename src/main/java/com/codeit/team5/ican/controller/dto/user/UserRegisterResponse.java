package com.codeit.team5.ican.controller.dto.user;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class UserRegisterResponse {
    private Long id;
    private String email;
    private String name;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
