package com.codeit.team5.ican.repository;

import com.codeit.team5.ican.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<Todo> findByTodoId(Long todoId);
}
