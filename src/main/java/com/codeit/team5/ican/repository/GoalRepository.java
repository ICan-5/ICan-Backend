package com.codeit.team5.ican.repository;

import com.codeit.team5.ican.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    Optional<Goal> findByUserIdAndGoalId(long userId, long goalId);
}
