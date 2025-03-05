package com.codeit.team5.ican.service;

import com.codeit.team5.ican.controller.dto.calendar.BasketTodoCreateRequest;
import com.codeit.team5.ican.domain.BasketTodo;
import com.codeit.team5.ican.domain.Goal;
import com.codeit.team5.ican.domain.Todo;
import com.codeit.team5.ican.domain.User;
import com.codeit.team5.ican.exception.BasketTodoNotFoundException;
import com.codeit.team5.ican.exception.GoalNotFoundException;
import com.codeit.team5.ican.exception.UserNotFoundException;
import com.codeit.team5.ican.repository.BasketTodoRepository;
import com.codeit.team5.ican.repository.GoalRepository;
import com.codeit.team5.ican.repository.TodoRepository;
import com.codeit.team5.ican.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final GoalRepository goalRepository;
    private final BasketTodoRepository basketTodoRepository;

    @Transactional(readOnly = true)
    public List<Todo> getMonthlyTodos(Long userId, int year, int month) {
        return todoRepository.getMonthlyTodos(userId, year, month);
    }

    @Transactional(readOnly = true)
    public List<Todo> getDailyTodos(Long userId, LocalDate date) {
        return todoRepository.getDailyTodos(userId, date);
    }

    @Transactional
    public BasketTodo createBasketTodo(Long userId, BasketTodoCreateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("유저 아이디 " + userId + "를 찾을 수 없습니다.")
        );

        Goal goal = null;
        if(request.getGoalId() != null) {
            goal = goalRepository.findByUserIdAndGoalId(userId, request.getGoalId()).orElseThrow(() ->
                    new GoalNotFoundException("골 아이디 " + request.getGoalId() + "를 찾을 수 없습니다.")
            );
        }

        return basketTodoRepository.save(BasketTodo.create(user, goal, request));
    }

    @Transactional(readOnly = true)
    public List<BasketTodo> getBasketTodos(Long userId) {
        return basketTodoRepository.findAllByUserIdOrderByCreatedAtAsc(userId);
    }

    @Transactional
    public void deleteBasketTodo(Long userId, Long basketTodoId) {
        BasketTodo basketTodo = basketTodoRepository.findByIdAndUserId(basketTodoId, userId).orElseThrow(() ->
                new BasketTodoNotFoundException("할 일 장바구니 아이디 " + basketTodoId + "를 찾을 수 없습니다.")
        );
        basketTodoRepository.delete(basketTodo);
    }

    @Transactional
    public void deleteAllBasketTodos(Long userId) {
        basketTodoRepository.deleteAllByUserId(userId);
    }
}

