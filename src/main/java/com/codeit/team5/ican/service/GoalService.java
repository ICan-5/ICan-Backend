package com.codeit.team5.ican.service;

import com.codeit.team5.ican.controller.dto.goal.GoalCreateRequest;
import com.codeit.team5.ican.controller.dto.goal.GoalUpdateRequest;
import com.codeit.team5.ican.domain.Goal;
import com.codeit.team5.ican.domain.User;
import com.codeit.team5.ican.exception.GoalNotFoundException;
import com.codeit.team5.ican.exception.UserNotFoundException;
import com.codeit.team5.ican.repository.GoalRepository;
import com.codeit.team5.ican.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
                new UserNotFoundException("유저 아이디 " + userId + "를 찾을 수 없습니다.")
        );

        return user.getGoals();
    }

    @Transactional
    public Goal createGoal(Long userId, GoalCreateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("유저 아이디 " + userId + "를 찾을 수 없습니다.")
        );

        try {
            Goal saved = goalRepository.save(Goal.create(user, request));
            goalRepository.flush(); //for checking DataIntegrityViolationException
            return saved;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Transactional
    public Goal updateGoal(Long userId, Long goalId, GoalUpdateRequest request) {
        Goal goal = findGoal(userId, goalId);
        goal.update(request);
        return goal;
    }

    @Transactional
    public void deleteGoal(Long userId, Long goalId) {
        goalRepository.delete(findGoal(userId, goalId));
    }

    @Transactional(readOnly = true)
    public Goal findGoalWithTodosAndBasketTodos(Long userId, Long goalId) {
        return goalRepository.findGoalWithTodosAndBasketTodos(userId, goalId).orElseThrow(() ->
                new GoalNotFoundException("골 아이디 " + goalId + "를 찾을 수 없습니다.")
        );
    }

    @Transactional(readOnly = true)
    protected Goal findGoal(Long userId, Long goalId) {
        return goalRepository.findByUserIdAndGoalId(userId, goalId).orElseThrow(() ->
                new GoalNotFoundException("골 아이디 " + goalId + "를 찾을 수 없습니다.")
        );
    }
}
