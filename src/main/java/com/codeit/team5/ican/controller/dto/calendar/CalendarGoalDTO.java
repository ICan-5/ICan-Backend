package com.codeit.team5.ican.controller.dto.calendar;

import com.codeit.team5.ican.domain.Goal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CalendarGoalDTO {
    private Long goalId;
    private String title;
    private String color;

    public static CalendarGoalDTO from(Goal goal) {
        if(goal == null) return null;
        return new CalendarGoalDTO(goal.getGoalId(), goal.getTitle(), goal.getColor());
    }
}
