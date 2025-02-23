package com.codeit.team5.ican.domain;

import com.codeit.team5.ican.controller.dto.todo.TodoCreateRequest;
import com.codeit.team5.ican.controller.dto.todo.TodoUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Todo {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private Long todoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id", nullable = false)
    private Goal goal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long noteId;

    private LocalDate date;

    private ZonedDateTime createdAt;

    private Boolean done;

    public static Todo create(User user, Goal goal, TodoCreateRequest request) {
        return Todo.builder()
                .title(request.getTitle())
                .todoId(request.getTodoId())
                .goal(goal)
                .user(user)
                .noteId(request.getNoteId())
                .date(request.getDate())
                .createdAt(request.getCreatedAt())
                .done(request.getDone())
                .build();
    }

    public void update(TodoUpdateRequest request) {
        this.title = request.getTitle() != null ? request.getTitle() : this.title;
        this.done = request.getDone() != null ? request.getDone() : this.done;
        this.date = request.getDate() != null ? request.getDate() : this.date;
    }

    public void update(Goal goal, TodoUpdateRequest request) {
        update(request);
        this.goal = goal;
    }

    public void deleteNote() {
        this.noteId = null;
    }
}
