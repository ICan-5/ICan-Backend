package com.codeit.team5.ican.repository;

import com.codeit.team5.ican.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    Goal findByGoalId(Long goalId);
    void deleteByGoalId(Long goalId);
}
