package com.codeit.team5.ican.domain;

import com.codeit.team5.ican.controller.dto.calendar.BasketTodoCreateRequest;
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
public class BasketTodo {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    private Goal goal;

    private ZonedDateTime createdAt;

    public static BasketTodo create(User user, Goal goal, BasketTodoCreateRequest request) {
        return BasketTodo.builder()
                .title(request.getTitle())
                .user(user)
                .goal(goal)
                .createdAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }
}
