package com.codeit.team5.ican.repository;

import com.codeit.team5.ican.domain.Goal;

import java.util.Optional;

public interface GoalRepositoryCustom {
    Optional<Goal> findGoalWithTodosAndBasketTodos(long userId, long goalId);
}
