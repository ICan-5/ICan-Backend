package com.codeit.team5.ican.repository;

import com.codeit.team5.ican.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryCustom {
    Optional<Todo> findByUserIdAndTodoId(long userId, long todoId);
    List<Todo> findAllByUserIdAndNoteId(Long userId, Long noteId);
}
