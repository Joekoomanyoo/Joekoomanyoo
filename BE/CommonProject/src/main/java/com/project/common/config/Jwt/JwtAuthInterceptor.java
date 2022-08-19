package com.project.common.config.Jwt;

import com.project.common.entity.User.UserEntity;
import com.project.common.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtAuthInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    private String HEADER_TOKEN_KEY = "X-AUTH-TOKEN";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{
        String token = request.getHeader(HEADER_TOKEN_KEY);
        UserEntity userEntity = userRepository.findByUserSeq(Integer.parseInt(jwtTokenProvider.getUserPk(token)));

        // 사용자가 없는 경우
        if(userEntity == null){
            return false;
        }


        // token에서 가져온 userSeq로 찾은 userId 와 token의 userId가 같다면 같은 유저
        if(userEntity.getUserId().equals(jwtTokenProvider.getUserId(token))){
            return true;
        }else{
            return false;
        }
    }
}
