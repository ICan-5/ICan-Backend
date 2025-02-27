package com.codeit.team5.ican.controller.dto.goal;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GoalCreateRequest {
    @NotNull
    private Long goalId;
    private String title;
}
