package com.project.common.config.auth;

import com.project.common.service.auth.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // JWT 인증 Filter 생성
    
    private JwtProvider jwtProvider;

    private CustomUserDetailService userDetailService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        try{
            String jwt = getToken(request);
            if(token != null && jwtProvider.validateJwtToken(token)){
            }
        }
    }

}
