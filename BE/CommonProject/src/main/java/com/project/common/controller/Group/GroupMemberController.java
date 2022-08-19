package com.project.common.controller.Group;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.config.Jwt.JwtTokenProvider;
import com.project.common.dto.Group.Request.ReqGroupJoinDto;
import com.project.common.dto.Group.Response.ResGroupMemberDto;
import com.project.common.service.Group.GroupMemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/group/{groupSeq}")
@Api(tags = {"모임 멤버 API"})
public class GroupMemberController {
    private final GroupMemberService groupMemberService;
    private final JwtTokenProvider jwtTokenProvider;
    
    @ApiOperation(value = "모임 멤버 목록 조회")
    @GetMapping("/member/list")
    public ResponseEntity<List<ResGroupMemberDto>> getMemberList(@PathVariable("groupSeq") int groupSeq) throws Exception{
    	return new ResponseEntity<>(groupMemberService.getMemberList(groupSeq),HttpStatus.OK);
    }
    
    @ApiOperation(value = "모임 참가 신청")
	@PostMapping("/member/join")
	public ResponseEntity<String> joinGroup(@RequestBody ReqGroupJoinDto groupJoinRequestDto, @PathVariable int groupSeq){
    	return new ResponseEntity<>(groupMemberService.joinGroup(groupSeq,groupJoinRequestDto),HttpStatus.CREATED);
	}
		
	@ApiOperation(value = "모임 탈퇴 / 가입 취소 ")
	@DeleteMapping("/member/leave")
	public ResponseEntity<String> leaveGroup(HttpServletRequest request,@PathVariable("groupSeq") int groupSeq){
   	 	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));
		return new ResponseEntity<>(groupMemberService.leaveGroup(groupSeq,userId),HttpStatus.OK);
	}
	
	@ApiOperation(value = "가입 거절 /강제 퇴장 ")
	@DeleteMapping("/member/out/{userSeq}")
	public ResponseEntity<String> outGroup(@PathVariable("userSeq") int userSeq,@PathVariable("groupSeq") int groupSeq){
   	 	return new ResponseEntity<>(groupMemberService.outGroup(groupSeq,userSeq),HttpStatus.OK);
	}
	
	@ApiOperation(value = "모임 가입 승인 - memberStatus 0(가입대기)->1(일반회원)")
	@PutMapping("/member/approve/{userSeq}")
	public ResponseEntity<String> approveMember(@PathVariable("groupSeq") int groupSeq,@PathVariable("userSeq") int userSeq){	
		return new ResponseEntity<>(groupMemberService.approveMember(groupSeq,userSeq),HttpStatus.OK);
	}
}
