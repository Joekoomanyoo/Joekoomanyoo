package com.project.common.controller;

import com.project.common.config.auth.JwtTokenProvider;
import com.project.common.dto.UserDto;
import com.project.common.dto.UserMapper;
import com.project.common.entity.UserEntity;
import com.project.common.repository.UserRepository;
import com.project.common.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@Api("UserController")
@RestController
@RequiredArgsConstructor    // autowired 안써도됨
@RequestMapping("/api/user")
public class UserController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 일반 회원 가입
     * @param userDto
     * @return success / fail
     */

    @ApiOperation(value = "일반 회원가입", response = String.class)
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@ApiParam(value="userId, password, userNickname, userBirth, socialLoginType, userGender, profileImgUrl, jwtToken, fcmToken, userRegistedAt, userUpdatedAt, isDeleted 받습니다.")
                                             @RequestBody UserDto userDto, BindingResult bindingResult) {

        // validation
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }

        // 초기 설정값
        userDto.setIsDeleted('N');
        userDto.setUserRegistedAt(LocalDateTime.now());
        userDto.setUserUpdatedAt(LocalDateTime.now());
        // DTO에 저장된 값을 Entity 값을 바꾸기
        // Service와 Controller 이동은 DTO
        // Controller와 DB 이동은 Entity
        if (userService.saveOrUpdateUser(UserMapper.MAPPER.toEntity(userDto))) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 회원 탈퇴 기능
     * @param userId
     * @return success / fail
     */

    @DeleteMapping("/resign/{userId}")
    @ApiOperation(value = "회원탈퇴기능", response = String.class)
    public ResponseEntity<String> resign(@ApiParam(value="사용자 ID ( Email )", required = true) @PathVariable("userId") String userId){
        // 회원이 존재하지 X -> 삭제 불가
        if(!userService.resignUser(userId)){
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
        // 회원이 존재 -> 삭제 완료
        else{
            return new ResponseEntity<String>(SUCCESS, HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * 일반 로그인, JWT 발급
     * @param userInfo
     * @return token
     */
    
    @ApiOperation(value = "일반 로그인", response = String.class)
    @PostMapping("/login")
    public ResponseEntity<String> login(@ApiParam(value="userId, userPassword", required = true) @RequestBody Map<String, String> userInfo) {
        UserDto userDto = userService.login(userInfo);

        // 정보가 없는경우
        if(userDto == null){
           return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }

        // 비밀번호가 틀린 경우
        if (!passwordEncoder.matches(userInfo.get("userPassword"), userDto.getUserPassword())) {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }

        String token = jwtTokenProvider.createToken(userDto.getUserSeq(),userDto.getUserId());

        return ResponseEntity.ok(token);
    }


    /**
     * 이메일 중복 검사
     * @param userId
     * @return success / fail
     */

    @GetMapping("/check/email/{userId}")
    @ApiOperation(value = "이메일 중복 검사", response = String.class)
    public ResponseEntity<?> checkEmail(@ApiParam(value="사용자 ID ( Email )", required = true)@PathVariable("userId") String userId){
        if(userService.checkEmail(userId)){
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }else{
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 닉네임 중복 검사
     * @param userNickname
     * @return success / fail
     */

    @GetMapping("/check/nickname/{userNickname}")
    @ApiOperation(value = "닉네임 중복 검사", response = String.class)
    public ResponseEntity<?> checkNickname(@ApiParam(value="사용자 닉네임 ( Nickname )", required = true)@PathVariable("userNickname") String userNickname){
        if(userService.checkNickname(userNickname)){
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }else{
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/tokenvalidate")
    @ApiOperation(value = "사용자의 jwt token이 유효한지 체크")
    public ResponseEntity<?> checkTokenValidate(HttpServletRequest request){

        // 인증 확인후 돌리기
        String token = request.getHeader("Authorization");

        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body(null);
    }


}

