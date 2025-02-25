package com.codeit.team5.ican.interceptor;

import com.codeit.team5.ican.exception.InvalidTokenException;
import com.codeit.team5.ican.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final AuthService authService;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            sendCustomError(response, HttpServletResponse.SC_UNAUTHORIZED, "Authorization 헤더에 토큰을 포함해야합니다.");
            return false;
        }

        try {
            String accessToken = authorizationHeader.substring(7);
            Long userId = authService.getUserId(accessToken);

            request.setAttribute("userId", userId);
            return true;
        } catch (InvalidTokenException e) {
            sendCustomError(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return false;
        } catch (Exception e) {
            sendCustomError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 오류 발생");
            return false;
        }
    }

    private void sendCustomError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", message);

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
