package com.project.common.dto;

import com.project.common.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignupDto {
    private int userSeq;
    private String userId;
    private String userNickname;
    private String userPassword;
    private String userBirth;
    private String socialLoginType;
    private char userGender;
    private String profileImgUrl;
    private String jwtToken;
    private String fcmToken;
    private Date userRegistedAt;
    private Date userUpdatedAt;
    private char isDeleted;

    // DTO -> Entity
    public UserEntity toEntity(){
        UserEntity userEntity = UserEntity.builder()
                .userSeq(0)
                .userId(userId)
                .userNickname(userNickname)
                .userPassword(userPassword)
                .userBirth(userBirth)
                .socialLoginType(socialLoginType)
                .userGender(userGender)
                .profileImgUrl("")
                .jwtToken(jwtToken)
                .fcmToken(fcmToken)
                .userRegistedAt(userRegistedAt)
                .userUpdatedAt(userUpdatedAt)
                .isDeleted('N')
                .build();

        return userEntity;
    }

}
