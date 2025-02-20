package com.codeit.team5.ican.controller.dto;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class RegisterResponse {
    private Long id;
    private String email;
    private String name;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
