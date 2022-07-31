package com.project.common.service;

import com.project.common.dto.UserDto;
import com.project.common.dto.UserMapper;
import com.project.common.entity.UserEntity;
import com.project.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserModifyServiceImpl implements UserModifyService{

    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDto userInfo(int userSeq) {
        // 사용자 번호로 사용자 찾기
        UserEntity userEntity = userRepository.findByUserSeq(userSeq);
        // Dto로 변환해서 전달
        return UserMapper.MAPPER.toDto(userEntity);
    }
}
