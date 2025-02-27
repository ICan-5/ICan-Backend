package com.codeit.team5.ican.controller;

import com.codeit.team5.ican.config.annotation.LoginUser;
import com.codeit.team5.ican.controller.dto.goal.GoalCreateRequest;
import com.codeit.team5.ican.controller.dto.goal.GoalDTO;
import com.codeit.team5.ican.controller.dto.goal.GoalDetailDTO;
import com.codeit.team5.ican.controller.dto.goal.GoalUpdateRequest;
import com.codeit.team5.ican.service.GoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/goals")
@RequiredArgsConstructor
public class
GoalController {

    private final GoalService goalService;

    @GetMapping
    public ResponseEntity<List<GoalDTO>> getAllGoals(
            @LoginUser Long userId
    ) {
        return ResponseEntity.ok(
                goalService.findAllGoals(userId)
                        .stream()
                        .map(GoalDTO::from)
                        .sorted(Comparator.comparing(GoalDTO::getCreatedAt))
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<GoalDTO> createGoal(
            @LoginUser Long userId,
            @RequestBody @Valid GoalCreateRequest goalCreateRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                GoalDTO.from(goalService.createGoal(userId, goalCreateRequest))
        );
    }

    @GetMapping("{goalId}")
    public ResponseEntity<GoalDetailDTO> getGoal(
            @LoginUser Long userId,
            @PathVariable Long goalId
    ) {
        return ResponseEntity.ok(
                GoalDetailDTO.from(goalService.findGoalWithTodosAndBasketTodos(userId, goalId))
        );
    }

    @PatchMapping("{goalId}")
    public ResponseEntity<GoalDTO> updateGoal(
            @LoginUser Long userId,
            @PathVariable Long goalId,
            @RequestBody GoalUpdateRequest updateGoalRequest
    ) {
        return ResponseEntity.ok(
                GoalDTO.from(goalService.updateGoal(userId, goalId, updateGoalRequest))
        );
    }

    @DeleteMapping("{goalId}")
    public ResponseEntity<Void> deleteGoal(
            @LoginUser Long userId,
            @PathVariable Long goalId
    ) {
        goalService.deleteGoal(userId, goalId);
        return ResponseEntity.noContent().build();
    }

}
