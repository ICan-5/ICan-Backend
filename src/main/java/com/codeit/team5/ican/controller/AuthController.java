package com.codeit.team5.ican.controller;

import com.codeit.team5.ican.config.annotation.RefreshToken;
import com.codeit.team5.ican.controller.dto.auth.*;
import com.codeit.team5.ican.controller.dto.auth.CodeitUserResponse;
import com.codeit.team5.ican.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final RestClient restClient;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Valid LoginRequest request
    ) {
        try {
            CodeitLoginResponse response = restClient.post()
                    .uri("/auth/login")
                    .body(request)
                    .retrieve()
                    .body(CodeitLoginResponse.class);

            authService.login(response);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            log.error("Error : 슬리드투두 API 요청 실패 - {}", e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error : 백엔드 서버 에러 - {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @RefreshToken String refreshToken
    ) {
        try {
            CodeitTokenRefreshResponse refreshResponse = restClient.post()
                    .uri("/auth/tokens")
                    .header("Authorization", "Bearer " + refreshToken)
                    .retrieve()
                    .body(CodeitTokenRefreshResponse.class);

            CodeitUserResponse userResponse = restClient.get()
                    .uri("/user")
                    .header("Authorization", "Bearer " + refreshResponse.getAccessToken())
                    .retrieve()
                    .body(CodeitUserResponse.class);

            authService.login(refreshResponse, userResponse);

            return ResponseEntity.status(HttpStatus.CREATED).body(new AccessTokenResponse(refreshResponse.getAccessToken()));
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            log.error("Error : 슬리드투두 API 요청 실패 - {}", e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error : 백엔드 서버 에러 - {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
