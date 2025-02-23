package com.codeit.team5.ican.repository;

import com.codeit.team5.ican.controller.dto.dashboard.Jandi;
import com.codeit.team5.ican.controller.dto.dashboard.TodoStats;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepositoryCustom {
    List<Jandi> getJandi(long userId, int year);

    TodoStats getTodoStats(long userId, LocalDate date);
}
