package com.codeit.team5.ican.controller.dto.calendar;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BasketTodoCreateRequest {
    @NotNull
    private String title;

    private Long goalId;
}
