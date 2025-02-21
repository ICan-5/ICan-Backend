package com.codeit.team5.ican.controller.dto.todo;

import lombok.Getter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
public class TodoCreateRequest {
    private Long noteId;
    private Long todoId;
    private Long goalId;
    private LocalDate date;
    private ZonedDateTime createdAt;
    private Boolean done;
    private String title;
}
