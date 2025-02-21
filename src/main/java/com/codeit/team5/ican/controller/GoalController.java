package com.codeit.team5.ican.controller;

import com.codeit.team5.ican.controller.dto.goal.GoalCreateRequest;
import com.codeit.team5.ican.controller.dto.goal.GoalDTO;
import com.codeit.team5.ican.controller.dto.goal.GoalUpdateRequest;
import com.codeit.team5.ican.service.GoalService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @GetMapping
    public ResponseEntity<List<GoalDTO>> getAllGoals(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
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
            HttpServletRequest request,
            @RequestBody GoalCreateRequest goalCreateRequest
    ) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.status(HttpStatus.CREATED).body(
                GoalDTO.from(goalService.createGoal(userId, goalCreateRequest))
        );
    }

    @GetMapping("{goalId}")
    public ResponseEntity<GoalDTO> getGoal(
            HttpServletRequest request,
            @PathVariable Long goalId
    ) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(
                GoalDTO.from(goalService.findGoal(userId, goalId))
        );
    }

    @PatchMapping("{goalId}")
    public ResponseEntity<GoalDTO> updateGoal(
            HttpServletRequest request,
            @PathVariable Long goalId,
            @RequestBody GoalUpdateRequest updateGoalRequest
    ) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(
                GoalDTO.from(goalService.updateGoal(userId, goalId, updateGoalRequest))
        );
    }

    @DeleteMapping("{goalId}")
    public ResponseEntity<Void> updateGoal(
            HttpServletRequest request,
            @PathVariable Long goalId
    ) {
        Long userId = (Long) request.getAttribute("userId");
        goalService.deleteGoal(userId, goalId);
        return ResponseEntity.noContent().build();
    }

}
