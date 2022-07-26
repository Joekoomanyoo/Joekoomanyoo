package com.project.common.controller;

import com.project.common.dto.UserDto;
import com.project.common.entity.UserEntity;
import com.project.common.service.UserService;
import com.project.common.service.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("UserController")
@RestController
@RequiredArgsConstructor    // autowired 안써도됨
@RequestMapping("/api/user")
public class UserController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final UserServiceImpl userService;



    @ApiOperation(value = "일반 회원가입, 입력을 성공하면 'success'를  실패하면 'fail'을 반환", response = String.class)
    // valid, bindingresult 는 validation 어노테이션이다
    @PostMapping("/signup")
    public ResponseEntity<String> signupUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){
        // 에러체크
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // DTO에 저장된 값을 Entity 값을 바꾸기
        // Service와 Controller 이동은 DTO
        // Controller와 DB 이동은 Entity
        if(userService.saveOrUpdateUser(userDto.toEntity())){
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }else{
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
          }
    }

    @ApiOperation(value = "일반 회원탈퇴, 탈퇴를 성공하면 'success'를  실패하면 'fail'을 반환", response = String.class)
    @DeleteMapping("/resign/{userSeq}")
    public ResponseEntity<String> resignUser(@PathVariable("userSeq") int userSeq) throws Exception{
        userService.resignUser(userSeq);


    }
}
