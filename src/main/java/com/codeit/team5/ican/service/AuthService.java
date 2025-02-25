package com.codeit.team5.ican.service;

import com.codeit.team5.ican.controller.dto.auth.CodeitLoginResponse;
import com.codeit.team5.ican.controller.dto.auth.CodeitTokenRefreshResponse;
import com.codeit.team5.ican.controller.dto.auth.CodeitUserResponse;
import com.codeit.team5.ican.domain.User;
import com.codeit.team5.ican.exception.InvalidTokenException;
import com.codeit.team5.ican.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;

    private void login(Long codeitUserId, String accessToken) {
        User findUser = userRepository.findByCodeitId(codeitUserId);
        if(findUser == null) {
            throw new RuntimeException("백엔드 API로 회원가입을 해야합니다.");
        }

        redisTemplate.opsForValue().set(accessToken, String.valueOf(findUser.getId()), Duration.ofHours(1));
    }

    public void login(CodeitLoginResponse loginResponse) {
        login(loginResponse.getUser().getId(), loginResponse.getAccessToken());
    }

    public void login(CodeitTokenRefreshResponse refreshResponse, CodeitUserResponse userResponse) {
        login(userResponse.getId(), refreshResponse.getAccessToken());
    }

    public Long getUserId(String accessToken) {
        String value = redisTemplate.opsForValue().get(accessToken);
        if(value == null) {
            throw new InvalidTokenException("로그인 정보가 존재하지 않습니다.");
        }
        return Long.parseLong(value);
    }

    public boolean isAccessToken(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }


}
