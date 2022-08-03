package com.project.common.controller;

import com.project.common.dto.UserDto;
import com.project.common.dto.UserModifyDto;
import com.project.common.service.UserModifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api("UserModifyController")
@RestController
@RequiredArgsConstructor    // autowired 안써도됨
@RequestMapping("/api/modify")
public class UserModifyController {

    private static final String SUCCESS ="success";
    private static final String FAIL ="fail";
    private final UserModifyService userModifyService;

    /**
     * 사용자 정보 불러오기
     * @param userSeq
     * @return userDto
     */

    @GetMapping("/{userSeq}")
    @ApiOperation(value = "사용자 정보 불러오기", response = UserDto.class)
    public ResponseEntity<UserDto> userInfo(@ApiParam(value="사용자 번호 ( userSeq ) ") @PathVariable("userSeq") int userSeq){
        return new ResponseEntity<UserDto>(userModifyService.userInfo(userSeq), HttpStatus.OK);
    }

    /**
     * 사용자 정보 변경하기
     * @param userModifyDto
     * @return success / fail
     */

    @PutMapping()
    @ApiOperation(value="사용자 정보 변경하기", response = String.class)
    public ResponseEntity<String> modifyInfo(@ApiParam(value="사용자 변경 정보 ( userModifyDto ) ") @RequestBody UserModifyDto userModifyDto){
        // 만약 성공적으로 수정했을 경우
        if(userModifyService.modifyInfo(userModifyDto)){
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        // 수정이 안되었을 경우
        else{
            return new ResponseEntity<String>(FAIL, HttpStatus.OK);
        }
    }

    /**
     * 사용자 비밀번호 확인하기
     * @param userInfo
     * @return success / fail
     */
    @GetMapping("/check-password")
    @ApiOperation(value="사용자 비밀번호 확인하기", response = String.class)
    public ResponseEntity<String> checkPassword(@ApiParam(value="사용자 비밀번호 확인 ( userPassword )") @RequestBody Map<String, String> userInfo){
        // 비밀번호가 맞으면
        if(userModifyService.checkPassword(userInfo)){
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        // 비밀번호가 틀리면
        else{
            return new ResponseEntity<String>(FAIL, HttpStatus.OK);
        }
    }
}
