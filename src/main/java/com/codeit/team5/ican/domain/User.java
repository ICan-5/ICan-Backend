package com.codeit.team5.ican.domain;

import com.codeit.team5.ican.controller.dto.RegisterResponse;
import com.codeit.team5.ican.controller.dto.UpdateRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

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

    private User(Long codeitId, String email, String name, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.codeitId = codeitId;
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User of(RegisterResponse response) {
        return new User(
                response.getId(),
                response.getEmail(),
                response.getName(),
                response.getCreatedAt(),
                response.getUpdatedAt()
        );
    }

    public void update(UpdateRequest request) {
        this.name = request.getName();
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
