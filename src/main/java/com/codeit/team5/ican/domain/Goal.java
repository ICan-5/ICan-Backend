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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private ZonedDateTime createdAt;

    @OneToMany(mappedBy = "goal", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private final Set<Todo> todos = new HashSet<>();

    @OneToMany(mappedBy = "goal", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private final Set<BasketTodo> basketTodos = new HashSet<>();

    public static Goal create(User user, GoalCreateRequest request) {
        return Goal.builder()
                .goalId(request.getGoalId())
                .user(user)
                .title(request.getTitle())
                .color("default")
                .createdAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }

    public void update(GoalUpdateRequest request) {
        this.color = request.getColor() != null ? request.getColor() : color;
        this.title = request.getTitle() != null ? request.getTitle() : title;
    }
}
