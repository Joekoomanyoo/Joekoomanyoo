package com.project.common.service;

import com.project.common.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService{

    boolean saveOrUpdateUser(UserEntity userEntity) throws Exception;

    void resignUser(int userSeq) throws Exception;
}
