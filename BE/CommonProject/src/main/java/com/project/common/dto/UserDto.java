package com.project.common.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
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
    private LocalDateTime userRegistedAt;
    private LocalDateTime userUpdatedAt;
    private char isDeleted;
}
