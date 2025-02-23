package com.codeit.team5.ican.service;

import com.codeit.team5.ican.controller.dto.dashboard.Jandi;
import com.codeit.team5.ican.controller.dto.dashboard.TodoStats;
import com.codeit.team5.ican.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TodoRepository todoRepository;

    @Transactional(readOnly = true)
    public List<Jandi> getJandi(Long userId, int year) {
        return todoRepository.getJandi(userId, year)
                .stream()
                .sorted(Comparator.comparing(Jandi::getDate))
                .toList();
    }

    @Transactional(readOnly = true)
    public TodoStats getTodoStats(Long userId, LocalDate date) {
        return todoRepository.getTodoStats(userId, date);
    }

}
