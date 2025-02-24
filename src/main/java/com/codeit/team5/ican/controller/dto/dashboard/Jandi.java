package com.codeit.team5.ican.controller.dto.dashboard;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Jandi {
    private final LocalDate date;
    private final Integer donePercent;

    public Jandi(LocalDate date, Double donePercent) {
        this.date = date;
        this.donePercent = donePercent.intValue();
    }
}
