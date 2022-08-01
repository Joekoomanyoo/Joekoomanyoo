package com.project.common.controller;

import com.project.common.config.auth.JwtTokenProvider;
import com.project.common.dto.UserDto;
import com.project.common.dto.UserMapper;
import com.project.common.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@Api("UserController")
@RestController
@RequiredArgsConstructor    // autowired 안써도됨
@RequestMapping("/api/social/user")
public class SocialUserController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    /**
     * 소셜 회원 가입
     * @param userDto
     * @return success / fail
     */
    @ApiOperation(value = "소셜 회원가입", response = String.class)
    @PostMapping("/signup")
    public ResponseEntity<String> socialSignup(@ApiParam(value="userId, password, userNickname, userBirth, socialLoginType, userGender, profileImgUrl, jwtToken, fcmToken, userRegistedAt, userUpdatedAt, isDeleted 받습니다.")
                                               @RequestBody UserDto userDto, BindingResult bindingResult) {

        // validation
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }

        // 초기 설정값
        userDto.setSocialLoginType("social");
        userDto.setIsDeleted('N');
        userDto.setUserRegistedAt(LocalDateTime.now());
        userDto.setUserUpdatedAt(LocalDateTime.now());
        // DTO에 저장된 값을 Entity 값을 바꾸기
        // Service와 Controller 이동은 DTO
        // Controller와 DB 이동은 Entity
        if (userService.saveOrUpdateUser(userDto)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }

}
