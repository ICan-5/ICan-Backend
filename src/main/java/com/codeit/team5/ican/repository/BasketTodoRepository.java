package com.codeit.team5.ican.repository;

import com.codeit.team5.ican.domain.BasketTodo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketTodoRepository extends JpaRepository<BasketTodo, Long>, BasketTodoRepositoryCustom {
    Optional<BasketTodo> findByIdAndUserId(long id, long userId);
}
