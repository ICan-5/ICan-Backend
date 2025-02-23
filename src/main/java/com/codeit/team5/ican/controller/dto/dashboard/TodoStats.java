package com.codeit.team5.ican.controller.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TodoStats {
    private LocalDate date;
    private Long total;
    private Integer completed;

    public TodoStats(Long total, Integer completed) {
        this.total = total;
        this.completed = completed;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
