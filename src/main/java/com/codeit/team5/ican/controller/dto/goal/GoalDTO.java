package com.codeit.team5.ican.controller.dto.goal;

import com.codeit.team5.ican.domain.Goal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
@Builder
public class GoalDTO {
    private Long goalId;

    private String color;

    private ZonedDateTime createdAt;

    public static GoalDTO from(Goal goal) {
        return GoalDTO.builder()
                .goalId(goal.getGoalId())
                .color(goal.getColor())
                .createdAt(goal.getCreatedAt())
                .build();
    }
}
