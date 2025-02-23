package com.codeit.team5.ican.controller.dto.calendar;

import com.codeit.team5.ican.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class
CalendarTodoDTO {
    private Long todoId;
    private LocalDate date;
    private ZonedDateTime createdAt;
    private String title;
    CalendarGoalDTO goal;

    public static CalendarTodoDTO from(Todo todo) {
        return CalendarTodoDTO.builder()
                .todoId(todo.getTodoId())
                .date(todo.getDate())
                .createdAt(todo.getCreatedAt())
                .title(todo.getTitle())
                .goal(CalendarGoalDTO.from(todo.getGoal()))
                .build();
    }
}
