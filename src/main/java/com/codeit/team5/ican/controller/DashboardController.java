package com.codeit.team5.ican.controller;

import com.codeit.team5.ican.config.annotation.LoginUser;
import com.codeit.team5.ican.controller.dto.dashboard.Jandi;
import com.codeit.team5.ican.controller.dto.dashboard.TodoStats;
import com.codeit.team5.ican.service.DashboardService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/jandi")
    public ResponseEntity<List<Jandi>> getJandi(
            @LoginUser Long userId,
            @RequestParam @Min(2000) @Max(2050) Integer year
    ) {
        return ResponseEntity.ok(dashboardService.getJandi(userId, year));
    }

    @GetMapping("/todo-stats")
    public ResponseEntity<TodoStats> getTodayStats(
            @LoginUser Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date //YYYY-MM-DD
    ) {
        validateDateRange(date);
        return ResponseEntity.ok(dashboardService.getTodoStats(userId, date));
    }

    private void validateDateRange(LocalDate date) {
        LocalDate minDate = LocalDate.of(2000, 1, 1);
        LocalDate maxDate = LocalDate.of(2050, 12, 31);
        if(date.isBefore(minDate) || date.isAfter(maxDate)) {
            throw new IllegalArgumentException("날짜는 2000년부터 2050년 사이만 입력할 수 있습니다.");
        }
    }
}
