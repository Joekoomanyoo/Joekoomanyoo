package com.project.common.controller;

import com.project.common.dto.UserDto;
import com.project.common.service.UserModifyServiceImpl;
import com.project.common.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("UserModifyController")
@RestController
@RequiredArgsConstructor    // autowired 안써도됨
@RequestMapping("/api/modify")
public class UserModifyController {

    private UserModifyServiceImpl userModifyService;

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


}
