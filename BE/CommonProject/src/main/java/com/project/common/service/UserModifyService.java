package com.project.common.service;

import com.project.common.dto.UserDto;
import com.project.common.dto.UserMapper;
import com.project.common.dto.UserModifyDto;
import com.project.common.entity.UserEntity;
import com.project.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class UserModifyService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto userInfo(int userSeq) {
        // 사용자 번호로 사용자 찾기
        UserEntity userEntity = userRepository.findByUserSeq(userSeq);
        // Dto로 변환해서 전달
        return UserMapper.MAPPER.toDto(userEntity);
    }

    @Transactional
    public boolean modifyInfo(UserModifyDto userModifyDto){
        // 변경할 사용자 번호
        int modifyUserSeq = userModifyDto.getUserSeq();
        // 사용자가 없을 경우
        if(userRepository.findByUserSeq(modifyUserSeq) == null){
            return false;
        }
        // 사용자가 있을 경우
        else{
            UserEntity userEntity = userRepository.findByUserSeq(modifyUserSeq);
            userEntity.setUserNickname(userModifyDto.getUserNickname());
            userEntity.setUserPassword(userModifyDto.getUserPassword());
            userEntity.setUserBirth(userModifyDto.getUserBirth());
            userEntity.setUserGender(userModifyDto.getUserGender());
            userEntity.setProfileImgUrl(userModifyDto.getProfileImgUrl());
            userEntity.setUserUpdatedAt(LocalDateTime.now());

            // 패스워드 암호화
            userEntity.encodePassword(this.passwordEncoder);
            
            userRepository.save(userEntity);
            return true;
        }
    }
}
