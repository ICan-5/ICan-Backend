package com.codeit.team5.ican.service;

import com.codeit.team5.ican.domain.Todo;
import com.codeit.team5.ican.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final TodoRepository todoRepository;

    @Transactional(readOnly = true)
    public List<Todo> getMonthlyTodos(Long userId, int year, int month) {
        return todoRepository.getMonthlyTodos(userId, year, month);
    }

    @Transactional(readOnly = true)
    public List<Todo> getDailyTodos(Long userId, LocalDate date) {
        return todoRepository.getDailyTodos(userId, date);
    }


}

