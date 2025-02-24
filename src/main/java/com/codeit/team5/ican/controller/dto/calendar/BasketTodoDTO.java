package com.codeit.team5.ican.controller.dto.calendar;

import com.codeit.team5.ican.domain.BasketTodo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
@Builder
public class BasketTodoDTO {
    private Long id;
    private String title;
    private Long goalId;
    private ZonedDateTime createdAt;

    public static BasketTodoDTO from(BasketTodo basketTodo) {
        return BasketTodoDTO.builder()
                .id(basketTodo.getId())
                .title(basketTodo.getTitle())
                .goalId(basketTodo.getGoal() == null? null : basketTodo.getGoal().getGoalId())
                .createdAt(basketTodo.getCreatedAt())
                .build();
    }
}
