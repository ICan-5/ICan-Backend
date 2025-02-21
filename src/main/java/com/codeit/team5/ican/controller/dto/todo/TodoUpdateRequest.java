package com.codeit.team5.ican.controller.dto.todo;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TodoUpdateRequest {
    private String title;
    private Long goalId;
    private Boolean done;
    private LocalDate date;
}
