package com.codeit.team5.ican.domain;

import com.codeit.team5.ican.controller.dto.goal.GoalCreateRequest;
import com.codeit.team5.ican.controller.dto.goal.GoalUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Goal {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private Long goalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String color;

    private ZonedDateTime createdAt;

    public static Goal create(User user, GoalCreateRequest request) {
        return Goal.builder()
                .user(user)
                .goalId(request.getGoalId())
                .color("default")
                .createdAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }

    public void update(GoalUpdateRequest request) {
        this.color = request.getColor();
    }

}
