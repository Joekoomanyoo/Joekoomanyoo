package com.project.common.service;

import com.project.common.dto.UserDto;
import com.project.common.entity.UserEntity;

public interface UserService{

    boolean saveOrUpdateUser(UserEntity userEntity) throws Exception;

    boolean resignUser(String userId) throws Exception;

    boolean checkEmail(String userId) throws Exception;

    boolean checkNickname(String userNickname) throws Exception;


}
