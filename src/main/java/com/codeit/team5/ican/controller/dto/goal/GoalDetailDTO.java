package com.codeit.team5.ican.controller.dto.goal;

import com.codeit.team5.ican.controller.dto.calendar.BasketTodoDTO;
import com.codeit.team5.ican.controller.dto.todo.TodoDTO;
import com.codeit.team5.ican.domain.Goal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class GoalDetailDTO {
    private Long goalId;

    private String title;

    private String color;

    private ZonedDateTime createdAt;

    private List<TodoDTO> todos;

    private List<BasketTodoDTO> basketTodos;

    public static GoalDetailDTO from(Goal goal) {
        return GoalDetailDTO.builder()
                .goalId(goal.getGoalId())
                .title(goal.getTitle())
                .color(goal.getColor())
                .createdAt(goal.getCreatedAt())
                .todos(
                        goal.getTodos().stream()
                                .map(TodoDTO::from)
                                .sorted(Comparator.comparing(TodoDTO::getCreatedAt))
                                .toList()
                )
                .basketTodos(
                        goal.getBasketTodos().stream()
                                .map(BasketTodoDTO::from)
                                .sorted(Comparator.comparing(BasketTodoDTO::getCreatedAt))
                                .toList()
                )
                .build();
    }
}
