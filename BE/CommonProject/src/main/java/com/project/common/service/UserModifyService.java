package com.project.common.service;

import com.project.common.dto.User.UserDto;
import com.project.common.dto.User.UserPasswordDto;
import com.project.common.mapper.User.UserMapper;
import com.project.common.dto.User.UserModifyDto;
import com.project.common.entity.User.UserEntity;
import com.project.common.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        
        // 사용자가 없거나 탈퇴된 경우
        if(userEntity == null || userEntity.getIsDeleted()=='Y'){
            return null;
        }
        // Dto로 변환해서 전달
        return UserMapper.MAPPER.toDto(userEntity);
    }


    // 사용자 정보 변경하기
    @Transactional
    public boolean modifyInfo(UserModifyDto userModifyDto){
        // 변경할 사용자 번호
        int modifyUserSeq = userModifyDto.getUserSeq();
        // 사용자가 없거나 이미 탈퇴한 경우 false
        if(userRepository.findByUserSeq(modifyUserSeq) == null || userRepository.findByUserSeq(modifyUserSeq).getIsDeleted()=='Y'){
            return false;
        }
        // 사용자가 있을 경우
        else{
                UserEntity userEntity = userRepository.findByUserSeq(modifyUserSeq);
                userEntity.setUserNickname(userModifyDto.getUserNickname());
                userEntity.setUserBirth(userModifyDto.getUserBirth());
                userEntity.setUserGender(userModifyDto.getUserGender());
                userEntity.setProfileImgUrl(userModifyDto.getProfileImgUrl());
                userEntity.setUserUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                // 비밀번호가 바뀐경우
                // 비밀번호가 안바뀐 경우에는 그냥 통과
                if(userModifyDto.getUserPassword() != null){
                    userEntity.setUserPassword(userModifyDto.getUserPassword());
                    // 패스워드 암호화
                    userEntity.encodePassword(this.passwordEncoder);
                }
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

    // 비밀번호 찾기 -> 바꾸기
    public boolean findPassword(UserPasswordDto userPasswordDto) {
        // 유저 정보 가져오기
        UserEntity userEntity = userRepository.findByUserId(userPasswordDto.getUserId());
        // 유저가 없는 경우 or 유저가 삭제된 경우 false
        if(userEntity == null || userEntity.getIsDeleted() == 'Y'){
            return false;
        }

        userEntity.setUserPassword(userPasswordDto.getUserPassword());
        // 패스워드 암호화
        userEntity.encodePassword(this.passwordEncoder);
        userRepository.save(userEntity);
        return true;

    }
}
