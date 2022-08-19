package com.project.common.controller.Group;


import java.util.Date;
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
import com.project.common.dto.Group.Request.ReqGroupScheduleDto;
import com.project.common.dto.Group.Response.ResGroupScheduleDto;
import com.project.common.service.Group.GroupScheduleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/group")
@Api(tags = {"모임 일정 API"})
public class GroupScheduleController {
    private final GroupScheduleService groupScheduleService;
    private final JwtTokenProvider jwtTokenProvider;
    
    @ApiOperation(value = "모임 일정 조회")
    @GetMapping("/{groupSeq}/schedule/list")
    public ResponseEntity<List<ResGroupScheduleDto>> getGroupSchedule(@PathVariable("groupSeq") int groupSeq) throws Exception{
    	return new ResponseEntity<>(groupScheduleService.getGroupSchedule(groupSeq),HttpStatus.OK);
    }
    
    @ApiOperation(value = "내 모임 일정 조회")
    @GetMapping("/my-schedule")
    public ResponseEntity<List<ResGroupScheduleDto>> getMyGroupSchedule(HttpServletRequest request) throws Exception{
   	 	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));
    	return new ResponseEntity<>(groupScheduleService.getMyGroupSchedule(userId),HttpStatus.OK);
    }
    
    @ApiOperation(value = "모임 일정 생성")	
    @PostMapping("/{groupSeq}/schedule/add")
    public ResponseEntity<String> createGroupSchedule(@RequestBody ReqGroupScheduleDto gsDto, @PathVariable int groupSeq){
    	return new ResponseEntity<>(groupScheduleService.createGroupSchedule(groupSeq,gsDto),HttpStatus.CREATED);
    }
    
   	@ApiOperation(value = "모임 일정 삭제")
   	@DeleteMapping("/{groupSeq}/schedule/delete")
   	public ResponseEntity<String> deleteGroupSchedule(@PathVariable int groupSeq, @RequestParam("gsSeq") int gsSeq){
   		return new ResponseEntity<>(groupScheduleService.deleteGroupSchedule(groupSeq,gsSeq),HttpStatus.OK);
   	}
   	
	//-------------------------------------유기된 기능----------------------------------------//
  	@ApiOperation(value = "모임 일정 수정")
  	@PutMapping("/{groupSeq}/schedule/modify")
  	public ResponseEntity<String> modifyGroupSchedule(@RequestBody ReqGroupScheduleDto gsDto, @PathVariable int groupSeq){
  		return new ResponseEntity<>(groupScheduleService.modifyGroupSchedule(groupSeq,gsDto),HttpStatus.OK);
  	}
}
