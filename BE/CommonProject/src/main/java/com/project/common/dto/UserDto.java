package com.project.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
//    private String socialLoginType;
//    private String profileImgUrl;
//    private String fcmToken;
//    private Date userRegistedAt;
//    private Date userUpdatedAt;
//    private char isDeleted;

}
