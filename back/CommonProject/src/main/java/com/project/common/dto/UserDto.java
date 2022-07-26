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
public class UserDto {
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
    private String userLng;
    private String userLat;

    // DTO -> Entity
    public UserEntity toEntity(){
        UserEntity userEntity = UserEntity.builder()
                .userSeq(userSeq)
                .userId(userId)
                .userNickname(userNickname)
                .userPassword(userPassword)
                .userBirth(userBirth)
                .socialLoginType(socialLoginType)
                .userGender(userGender)
                .profileImgUrl(profileImgUrl)
                .jwtToken(jwtToken)
                .fcmToken(fcmToken)
                .userRegistedAt(userRegistedAt)
                .userUpdatedAt(userUpdatedAt)
                .isDeleted(isDeleted)
                .userLng(userLng)
                .userLat(userLat).build();

        return userEntity;
    }

}
