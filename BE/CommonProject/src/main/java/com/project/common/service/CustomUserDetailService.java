package com.project.common.service;

import com.project.common.repository.User.UserLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserLoginRepository userLoginRepository;

    @Override
    public UserDetails loadUserByUsername(String userSeq) throws UsernameNotFoundException {
        return userLoginRepository.findByUserSeq(Integer.parseInt(userSeq))
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

}