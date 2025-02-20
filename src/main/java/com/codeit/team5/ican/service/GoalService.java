package com.codeit.team5.ican.service;

import com.codeit.team5.ican.controller.dto.goal.GoalCreateRequest;
import com.codeit.team5.ican.controller.dto.goal.UpdateGoalRequest;
import com.codeit.team5.ican.domain.Goal;
import com.codeit.team5.ican.domain.User;
import com.codeit.team5.ican.exception.GoalNotFoundException;
import com.codeit.team5.ican.repository.GoalRepository;
import com.codeit.team5.ican.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final UserRepository userRepository;
    private final GoalRepository goalRepository;

    @Transactional(readOnly = true)
    public List<Goal> findAllGoals(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("유저 아이디 " + userId + "를 찾을 수 없습니다.")
        );

        return user.getGoals();
    }

    @Transactional
    public Goal createGoal(Long userId, GoalCreateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("유저 아이디 " + userId + "를 찾을 수 없습니다.")
        );
        return goalRepository.save(Goal.create(user, request));
    }

    @Transactional(readOnly = true)
    public Goal findGoal(Long userId, Long goalId) {
        Goal goal = goalRepository.findByGoalId(goalId);

        if(goal == null || !userId.equals(goal.getUser().getId())) {
            throw new GoalNotFoundException("골 아이디 " + goalId + "를 찾을 수 없습니다.");
        }

        return goal;
    }

    @Transactional
    public Goal updateGoal(Long userId, Long goalId, UpdateGoalRequest request) {
        Goal goal = findGoal(userId, goalId);
        goal.update(request);
        return goalRepository.save(goal);
    }

    @Transactional
    public void deleteGoal(Long userId, Long goalId) {
        Goal goal = findGoal(userId, goalId);
        goalRepository.deleteByGoalId(goal.getGoalId());
    }

}
