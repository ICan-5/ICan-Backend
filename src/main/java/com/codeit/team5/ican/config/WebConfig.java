package com.codeit.team5.ican.config;

import com.codeit.team5.ican.config.annotation.RefreshTokenArgumentResolver;
import com.codeit.team5.ican.config.annotation.UserIdArgumentResolver;
import com.codeit.team5.ican.interceptor.LoginInterceptor;
import com.codeit.team5.ican.interceptor.RefreshTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final RefreshTokenInterceptor refreshTokenInterceptor;

    private final UserIdArgumentResolver userIdArgumentResolver;
    private final RefreshTokenArgumentResolver refreshTokenArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns("/api/v1/auth/login")
                .excludePathPatterns("/api/v1/user/register")
                .excludePathPatterns("/api/v1/auth/refresh");

        registry.addInterceptor(refreshTokenInterceptor)
                .addPathPatterns("/api/v1/auth/refresh");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userIdArgumentResolver);
        resolvers.add(refreshTokenArgumentResolver);
    }
}
