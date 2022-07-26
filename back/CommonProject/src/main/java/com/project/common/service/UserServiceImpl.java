package com.project.common.service;

import com.project.common.entity.UserEntity;
import com.project.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean saveOrUpdateUser(UserEntity userEntity){
        // 패스워스 바꿔서 변경
        userEntity.encodePassword(this.passwordEncoder);

        if(userRepository.save(userEntity) != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void resignUser(int userSeq) throws Exception {
        userRepository.deleteById(userSeq);
    }
}
