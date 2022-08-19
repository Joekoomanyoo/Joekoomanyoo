package com.project.common.dto.User;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Api("회원비밀번호찾기 Dto")
public class UserPasswordDto {
    private String userId;
    private String userPassword;
}
