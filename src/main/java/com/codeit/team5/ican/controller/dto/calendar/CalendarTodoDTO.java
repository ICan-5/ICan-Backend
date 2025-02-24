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
    private Long noteId;
    private LocalDate date;
    private ZonedDateTime createdAt;
    private String title;
    private Boolean done;
    CalendarGoalDTO goal;

    public static CalendarTodoDTO from(Todo todo) {
        return CalendarTodoDTO.builder()
                .todoId(todo.getTodoId())
                .noteId(todo.getNoteId())
                .date(todo.getDate())
                .createdAt(todo.getCreatedAt())
                .title(todo.getTitle())
                .done(todo.getDone())
                .goal(CalendarGoalDTO.from(todo.getGoal()))
                .build();
    }
}
