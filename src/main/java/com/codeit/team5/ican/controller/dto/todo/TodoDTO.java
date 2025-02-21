package com.codeit.team5.ican.controller.dto.todo;

import com.codeit.team5.ican.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
@Builder
public class TodoDTO {
    private Long noteId;
    private Long todoId;
    private Long goalId;
    private LocalDate date;
    private ZonedDateTime createdAt;
    private Boolean done;
    private String title;

    public static TodoDTO from(Todo todo) {
        return TodoDTO.builder()
                .noteId(todo.getNoteId())
                .todoId(todo.getTodoId())
                .goalId(todo.getGoal().getGoalId())
                .date(todo.getDate())
                .createdAt(todo.getCreatedAt())
                .done(todo.getDone())
                .title(todo.getTitle())
                .build();
    }

}
