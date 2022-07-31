package com.project.common.controller;


import com.project.common.dto.GroupAttributeDto;
import com.project.common.dto.GroupDto;
import com.project.common.dto.UserSignupDto;
import com.project.common.service.GroupMemberService;
import com.project.common.service.GroupService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/group/{groupSeq}/participants")
public class GroupMemberController {
    private final GroupService groupService;
    private final GroupMemberService groupMemberService;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
//    //참가 신청
//    @ApiOperation(value = "모임 개설, 모임 개설이 성공하면, 모임 정보(GroupDto) 반환")
//    @PostMapping("/join")
//    public ResponseEntity<String> joinGroup(@PathVariable long groupSeq, @RequestBody UserSignupDto userSignupDto){
//    	return new ResponseEntity<>(groupService.joinGroup(groupSeq), HttpStatus.CREATED);
//    }
    
    // 그륩 탈퇴
    @ApiOperation(value = "모임 전체 목록 조회, 모임 정보(GroupDto) 반환")
    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> withdrawGroup(@PathVariable long groupSeq, @RequestBody UserSignupDto userSignupDto) throws Exception{
    	groupMemberService.withdrawGroup(groupSeq,userSignupDto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
//  //내 모임 목록 조회
//    @ApiOperation(value = "내 모임 목록 조회, 모임 정보(GroupDto) 반환")
//    @GetMapping("/list/user/{userSeq}")
//    public ResponseEntity<List<GroupDto>> getMyGroupList(@PathVariable("userSeq") String userSeq) throws Exception{
//    	return new ResponseEntity<>(groupService.getMyGroupList(userSeq),HttpStatus.OK);
//    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //모임 기본 정보 수정

   
    
//    //모임 시작
//    @PatchMapping("/{groupSeq}/start")
//    public ResponseEntity<?> startGroup(@PathVariable("groupSeq") Long groupSeq){
//    	return new ResponseEntity<>(groupService.startGroup(groupSeq),HttpStatus.OK);
//    }
//    
//  //모임 종료
//    @PatchMapping("/{groupSeq}/finish")
//    public ResponseEntity<?> finishGroup(@PathVariable("groupSeq") Long groupSeq){
//    	return new ResponseEntity<>(groupService.finishGroup(groupSeq),HttpStatus.OK);
//    }
//    
//    //모임 수락
//    @PatchMapping("/{groupSeq}/approve")
//    public ResponseEntity<?> startGroup(@PathVariable("groupSeq") Long groupSeq){
//    	return new ResponseEntity<>(groupService.startGroup(groupSeq),HttpStatus.OK);
//    }
//    
//  //모임 거절
//    @PatchMapping("/{groupSeq}/reject")
//    public ResponseEntity<?> finishGroup(@PathVariable("groupSeq") Long groupSeq){
//    	return new ResponseEntity<>(groupService.finishGroup(groupSeq),HttpStatus.OK);
//    }
//    
//    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////
    
}
