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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.config.Jwt.JwtTokenProvider;
import com.project.common.dto.Group.GroupDto;
import com.project.common.dto.Group.GroupSettingDto;
import com.project.common.dto.Group.Request.ReqGroupDto;
import com.project.common.dto.Group.Response.ResMyGroupDto;
import com.project.common.service.Group.GroupService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/group")
@Api(tags = {"모임 관리 API"})
public class GroupController {
    private final GroupService groupService;
    private final JwtTokenProvider jwtTokenProvider;
    
    @ApiOperation(value = "모임 개설")
    @PostMapping("/add")
    public ResponseEntity<GroupDto> addGroup(HttpServletRequest request, @RequestBody ReqGroupDto groupDto){
   	 	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));
    	return new ResponseEntity<>(groupService.addGroup(userId,groupDto), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "모임 리스트 조회")
    @GetMapping("/list")
    public ResponseEntity<List<GroupDto>> getGroupList() throws Exception{
    	return new ResponseEntity<>(groupService.getGroupList(),HttpStatus.OK);
    }
    
    @ApiOperation(value = "모임 상세 조회")
    @GetMapping("/{groupSeq}/info")
    public ResponseEntity<GroupDto> getGroupInfo(@PathVariable("groupSeq") int groupSeq){
    	return new ResponseEntity<>(groupService.getGroupInfo(groupSeq),HttpStatus.OK);
    }
    
    @ApiOperation(value = "모임 삭제")
    @DeleteMapping("/{groupSeq}/delete")
    public ResponseEntity<String> deleteGroup(@PathVariable("groupSeq") int groupSeq){  
   	 	return new ResponseEntity<>(groupService.deleteGroup(groupSeq),HttpStatus.OK);
    }
    
    @ApiOperation(value = "모임 정보 수정")
    @PutMapping("/{groupSeq}/modify")
    public ResponseEntity<GroupDto> updateGroup(@PathVariable("groupSeq") int groupSeq, @RequestBody ReqGroupDto groupDto){
    	return new ResponseEntity<>(groupService.updateGroup(groupSeq,groupDto),HttpStatus.OK);
    }
    
    @ApiOperation(value = "내 모임 목록 조회")
    @GetMapping("/my-group")
    public ResponseEntity<List<ResMyGroupDto>> getMyGroupList(HttpServletRequest request) throws Exception{
   	 	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));
   	 	return new ResponseEntity<>(groupService.getMyGroupList(userId),HttpStatus.OK);
    }
    
    @ApiOperation(value = "모임 테마 이미지 변경")
    @PutMapping("/{groupSeq}/modify/image")
    public ResponseEntity<String> updateGroup(@PathVariable("groupSeq") int groupSeq,@RequestParam("groupImgUrl") String groupImgUrl){
    	return new ResponseEntity<>(groupService.updateGroupImage(groupSeq,groupImgUrl),HttpStatus.OK);
    }
    
    @ApiOperation(value = "모임 상태 변경 - R(모집), O(진행), F(종료)")
    @PutMapping("/{groupSeq}/status")
    public ResponseEntity<String> changeStatus(@PathVariable("groupSeq") int groupSeq, @RequestBody GroupSettingDto groupSettingDto){
    	return new ResponseEntity<>(groupService.changeStatus(groupSeq,groupSettingDto),HttpStatus.OK);
    }
    
    @ApiOperation(value = "모임 활성화 여부 - Y(활성화), N(비활성화)")
    @PutMapping("/{groupSeq}/active")
    public ResponseEntity<String> changeActive(@PathVariable("groupSeq") int groupSeq, @RequestBody GroupSettingDto groupSettingDto){
    	return new ResponseEntity<>(groupService.changeActive(groupSeq,groupSettingDto),HttpStatus.OK);
    }

}
