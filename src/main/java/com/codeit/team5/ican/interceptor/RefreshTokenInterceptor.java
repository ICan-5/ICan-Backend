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
public class RefreshTokenInterceptor implements HandlerInterceptor {

    private final AuthService authService;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            sendCustomError(response, HttpServletResponse.SC_UNAUTHORIZED, "Authorization 헤더에 Refresh Token을 포함해야합니다.");
            return false;
        }

        String token = authorizationHeader.substring(7);
        if(authService.isAccessToken(token)) {
            sendCustomError(response, HttpServletResponse.SC_UNAUTHORIZED, "Access Token이 아닌 Refresh Token을 사용해야 합니다.");
            return false;
        }

        request.setAttribute("refreshToken", token);
        return true;
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
