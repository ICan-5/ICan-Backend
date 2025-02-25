package com.codeit.team5.ican.controller;

import com.codeit.team5.ican.config.annotation.LoginUser;
import com.codeit.team5.ican.controller.dto.user.UserRegisterRequest;
import com.codeit.team5.ican.controller.dto.auth.CodeitUserResponse;
import com.codeit.team5.ican.controller.dto.user.UserUpdateRequest;
import com.codeit.team5.ican.controller.dto.user.UserDTO;
import com.codeit.team5.ican.service.UserService;
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
            @RequestBody UserRegisterRequest request
    ) {
        try {
            CodeitUserResponse response = restClient.post()
                    .uri("/user")
                    .body(request)
                    .retrieve()
                    .body(CodeitUserResponse.class);

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
    public ResponseEntity<UserDTO> getUser(@LoginUser Long userId) {
        return ResponseEntity.ok(UserDTO.from(userService.findByUserId(userId)));
    }

    @PostMapping
    public ResponseEntity<UserDTO> updateUser(
            @LoginUser Long userId,
            @RequestPart(required = false) MultipartFile image,
            @RequestPart(required = false, name = "user") UserUpdateRequest updateRequest
    ) {
        return ResponseEntity.ok(UserDTO.from(userService.updateUser(userId, image, updateRequest)));
    }

}
