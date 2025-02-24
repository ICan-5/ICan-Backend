package com.codeit.team5.ican.repository;

import com.codeit.team5.ican.domain.BasketTodo;

import java.util.List;

public interface BasketTodoRepositoryCustom {
    List<BasketTodo> findAllByUserIdOrderByCreatedAtAsc(long userId);
}
