package com.codeit.team5.ican.controller.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Jandi {
    private LocalDate date;
    private Long count;
}
