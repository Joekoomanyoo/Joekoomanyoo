package com.project.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

import com.project.common.entity.GroupEntity;
import com.project.common.entity.UserEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private int userSeq;
    private String userId;
    private String userNickname;
    private String userPassword;
    private String userBirth;
    private char userGender;
    private String socialLoginType;
    private String profileImgUrl;
    private String fcmToken;
    private LocalDateTime userRegistedAt;
    private LocalDateTime userUpdatedAt;
    private char isDeleted;
    private int evalCnt;
    private int evalList1;
    private int evalList2;
    private int evalList3;
    private int evalList4;
    private int evalList5;
    private LocalDateTime evalUpdatedAt;

}
