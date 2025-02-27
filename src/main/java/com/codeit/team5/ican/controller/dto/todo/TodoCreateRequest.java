package com.codeit.team5.ican.controller.dto.todo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
public class TodoCreateRequest {
    @NotNull
    private Long todoId;
    @NotNull
    private String title;
    @NotNull
    private ZonedDateTime createdAt;
    @NotNull
    private Boolean done;
    @NotNull
    private LocalDate date;

    private Long noteId;
    private Long goalId;
}
