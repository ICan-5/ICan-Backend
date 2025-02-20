package com.codeit.team5.ican.controller;

import com.codeit.team5.ican.controller.dto.RegisterRequest;
import com.codeit.team5.ican.controller.dto.RegisterResponse;
import com.codeit.team5.ican.controller.dto.UpdateRequest;
import com.codeit.team5.ican.domain.User;
import com.codeit.team5.ican.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UserController {

    private final RestClient restClient;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {
        try {
            RegisterResponse response = restClient.post()
                    .uri("/user")
                    .body(request)
                    .retrieve()
                    .body(RegisterResponse.class);

            userService.register(response);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            log.error("Error : 슬리드투두 API 요청 실패 - {}", e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<User> getUser(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(userService.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<User> updateUser(
            HttpServletRequest request,
            @RequestPart(required = false) MultipartFile image,
            @RequestPart(required = false, name = "user") UpdateRequest updateRequest
    ) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(userService.updateUser(userId, image, updateRequest));
    }

}
