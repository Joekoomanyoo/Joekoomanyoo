package com.project.common.service.auth;

import com.project.common.dto.UserRequestLoginDTO;
import com.project.common.entity.UserEntity;
import com.project.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException{
        UserEntity userEntity = userRepository.findOneByUserId(id).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        UserRequestLoginDTO requestLoginDTO = new UserRequestLoginDTO(userEntity.getUserId(), userEntity.getUserPassword());
        return (UserDetails) requestLoginDTO;
    }

}
