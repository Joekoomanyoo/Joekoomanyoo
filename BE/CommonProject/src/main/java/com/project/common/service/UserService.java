package com.project.common.service;

import com.project.common.config.auth.JwtTokenProvider;
import com.project.common.dto.UserDto;
import com.project.common.dto.UserMapper;
import com.project.common.entity.UserEntity;
import com.project.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public boolean saveOrUpdateUser(UserEntity userEntity){
        // 패스워스 암호화
        userEntity.encodePassword(this.passwordEncoder);

        if(userRepository.save(userEntity) != null){
            return true;
        }else{
            return false;
        }
    }

    // 회원 탈퇴
    @Transactional
    public boolean resignUser(String userId){
        // 만약 사용자가 존재하지 않는다면
        if(userRepository.findByUserId(userId) == null){
            return false;
        }else{
            // 사용자가 존재하면 삭제
            userRepository.deleteByUserId(userId);
            return true;
        }
    }

    // 로그인
    @Transactional
    public UserDto login(Map<String,String> userInfo){
        UserEntity loginUser = userRepository.findByUserId(userInfo.get("userId"));
        UserDto userDto = UserMapper.MAPPER.toDto(loginUser);
        return userDto;
    }

    // 이메일 중복 검사
    @Transactional
    public boolean checkEmail(String userId){
        // 만약 중복된 것이 없다면 이메일 생성 가능
        if(userRepository.findByUserId(userId)==null){
            return true;
        }else{
            return false;
        }
    }

    @Transactional
    public boolean checkNickname(String userNickname) {
        // 만약 중복된 것이 없다면 닉네임 생성 가능
        if(userRepository.findByUserNickname(userNickname) == null){
            return true;
        }else{
            return false;
        }
    }


}
