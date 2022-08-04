package com.project.common.dto;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Api("회원수정 Dto")
public class UserModifyDto {
    private int userSeq;
    private String userNickname;
    private String userPassword;
    private String userBirth;
    private char userGender;
    private String profileImgUrl;
    private LocalDateTime userUpdatedAt;
}