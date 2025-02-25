package com.codeit.team5.ican.domain;

import com.codeit.team5.ican.controller.dto.auth.CodeitUserResponse;
import com.codeit.team5.ican.controller.dto.user.UserUpdateRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private Long codeitId;

    private String email;

    private String name;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private String profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<Goal> goals = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<Todo> todos = new ArrayList<>();

    private User(Long codeitId, String email, String name, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.codeitId = codeitId;
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User of(CodeitUserResponse response) {
        return new User(
                response.getId(),
                response.getEmail(),
                response.getName(),
                response.getCreatedAt(),
                response.getUpdatedAt()
        );
    }

    public void update(UserUpdateRequest request) {
        this.name = request.getName();
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
