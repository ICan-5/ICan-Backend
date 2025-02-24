package com.codeit.team5.ican.controller;

import com.codeit.team5.ican.config.annotation.LoginUser;
import com.codeit.team5.ican.controller.dto.calendar.BasketTodoCreateRequest;
import com.codeit.team5.ican.controller.dto.calendar.BasketTodoDTO;
import com.codeit.team5.ican.controller.dto.calendar.CalendarTodoDTO;
import com.codeit.team5.ican.service.CalendarService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/monthly-todos")
    public ResponseEntity<List<CalendarTodoDTO>> getMonthlyTodos(
            @LoginUser Long userId,
            @RequestParam @Min(2000) @Max(2050) Integer year,
            @RequestParam @Min(1) @Max(12) Integer month
    ) {
        return ResponseEntity.ok(
                calendarService.getMonthlyTodos(userId, year, month)
                        .stream()
                        .map(CalendarTodoDTO::from)
                        .toList()
        );
    }

    @GetMapping("/daily-todos")
    public ResponseEntity<List<CalendarTodoDTO>> getDailyTodos(
            @LoginUser Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date //YYYY-MM-DD
    ) {
        validateDateRange(date);
        return ResponseEntity.ok(
                calendarService.getDailyTodos(userId, date)
                        .stream()
                        .map(CalendarTodoDTO::from)
                        .toList()
        );
    }

    @PostMapping("/todo-basket")
    public ResponseEntity<BasketTodoDTO> createBasketTodo(
            @LoginUser Long userId,
            @RequestBody BasketTodoCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                BasketTodoDTO.from(calendarService.createBasketTodo(userId, request))
        );
    }

    @GetMapping("/todo-basket")
    public ResponseEntity<List<BasketTodoDTO>> getBasketTodo(
            @LoginUser Long userId
    ) {
        return ResponseEntity.ok(
                calendarService.getBasketTodos(userId)
                        .stream()
                        .map(BasketTodoDTO::from)
                        .toList()
        );
    }

    @DeleteMapping("/todo-basket/{basketTodoId}")
    public ResponseEntity<Void> deleteBasketTodo(
            @LoginUser Long userId,
            @PathVariable Long basketTodoId
    ) {
        calendarService.deleteBasketTodo(userId, basketTodoId);
        return ResponseEntity.noContent().build();
    }

    private void validateDateRange(LocalDate date) {
        LocalDate minDate = LocalDate.of(2000, 1, 1);
        LocalDate maxDate = LocalDate.of(2050, 12, 31);
        if(date.isBefore(minDate) || date.isAfter(maxDate)) {
            throw new IllegalArgumentException("날짜는 2000년부터 2050년 사이만 입력할 수 있습니다.");
        }
    }
}
