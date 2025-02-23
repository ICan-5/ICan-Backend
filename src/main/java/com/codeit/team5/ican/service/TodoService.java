package com.codeit.team5.ican.service;

import com.codeit.team5.ican.controller.dto.todo.TodoCreateRequest;
import com.codeit.team5.ican.controller.dto.todo.TodoUpdateRequest;
import com.codeit.team5.ican.domain.Goal;
import com.codeit.team5.ican.domain.Todo;
import com.codeit.team5.ican.domain.User;
import com.codeit.team5.ican.exception.GoalNotFoundException;
import com.codeit.team5.ican.exception.TodoAlreadyExistsException;
import com.codeit.team5.ican.exception.TodoNotFoundException;
import com.codeit.team5.ican.exception.UserNotFoundException;
import com.codeit.team5.ican.repository.GoalRepository;
import com.codeit.team5.ican.repository.TodoRepository;
import com.codeit.team5.ican.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    @Transactional
    public Todo createTodo(Long userId, TodoCreateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("유저 아이디 " + userId + "를 찾을 수 없습니다.")
        );

        Goal goal = goalRepository.findByUserIdAndGoalId(userId, request.getGoalId()).orElseThrow(() ->
            new GoalNotFoundException("골 아이디 " + request.getGoalId() + "를 찾을 수 없습니다.")
        );

        try {
            Todo saved = todoRepository.save(Todo.create(user, goal, request));
            todoRepository.flush();
            return saved;
        } catch (DataIntegrityViolationException e) {
            throw new TodoAlreadyExistsException("할 일 아이디 " + request.getTodoId() + "가 이미 존재합니다");
        }
    }

    @Transactional
    public Todo updateTodo(Long userId, Long todoId, TodoUpdateRequest request) {
        Todo todo = findTodo(userId, todoId);

        //Goal을 변경하지 않는 경우
        if(request.getGoalId() == null) {
            todo.update(request);
            return todoRepository.save(todo);
        }

        Goal goal = goalRepository.findByUserIdAndGoalId(userId, request.getGoalId()).orElseThrow(() ->
                new GoalNotFoundException("골 아이디 " + request.getGoalId() + "를 찾을 수 없습니다.")
        );
        todo.update(goal, request);
        return todoRepository.save(todo);
    }

    @Transactional
    public void deleteTodo(Long userId, Long todoId) {
        todoRepository.delete(findTodo(userId, todoId));
    }

    @Transactional(readOnly = true)
    public Todo findTodo(Long userId, Long todoId) {
        return todoRepository.findByUserIdAndTodoId(userId, todoId).orElseThrow(() ->
                new TodoNotFoundException("할 일 아이디 " + todoId + "를 찾을 수 없습니다.")
        );
    }


}
