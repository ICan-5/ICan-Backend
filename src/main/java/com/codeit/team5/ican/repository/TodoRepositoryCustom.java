package com.codeit.team5.ican.repository;

import com.codeit.team5.ican.controller.dto.calendar.CalendarTodoDTO;
import com.codeit.team5.ican.controller.dto.dashboard.Jandi;
import com.codeit.team5.ican.controller.dto.dashboard.TodoStats;
import com.codeit.team5.ican.domain.Todo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodoRepositoryCustom {
    List<Jandi> getJandi(long userId, int year);

    TodoStats getTodoStats(long userId, LocalDate date);

    List<Todo> getMonthlyTodos(long userId, int year, int month);

    List<Todo> getDailyTodos(long userId, LocalDate date);

    Optional<Todo> findByUserIdAndTodoIdWithGoal(long userId, long todoId);
}
