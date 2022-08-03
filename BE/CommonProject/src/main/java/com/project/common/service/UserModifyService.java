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
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class UserModifyService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    // 사용자 정보 불러오기
    @Transactional
    public UserDto userInfo(int userSeq) {
        // 사용자 번호로 사용자 찾기
        UserEntity userEntity = userRepository.findByUserSeq(userSeq);
        // Dto로 변환해서 전달
        return UserMapper.MAPPER.toDto(userEntity);
    }


    // 사용자 정보 변경하기
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

    // 비밀번호 확인
    @Transactional
    public boolean checkPassword(Map<String, String> userInfo){
        UserEntity userEntity = userRepository.findByUserSeq(Integer.parseInt(userInfo.get("userSeq")));

        // 만약 비밀번호가 다를 경우
        if(!passwordEncoder.matches(userInfo.get("userPassword"),userEntity.getUserPassword())){
            return false;
        }
        // 비밀번호가 같을 경우
        else{
            return true;
        }
    }
}
