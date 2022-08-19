package com.project.common.config;

import com.project.common.config.Jwt.JwtAuthInterceptor;
import com.project.common.config.Jwt.JwtTokenProvider;
import com.project.common.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // interceptor 설정
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new JwtAuthInterceptor(jwtTokenProvider,userRepository))
                .addPathPatterns("/**")
                .excludePathPatterns("/api/chat/**", "/stomp/chat/**","/api/user/signup" , "/api/user/emailAuth/**", "/api/user/login", "/api/user/check/**", "/api/user/social/**","/api/modify/find-password", "/swagger-resources/**" , "/swagger-ui/**","/v2/api-docs", "/webjars/**");
    }
}
