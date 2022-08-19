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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.config.Jwt.JwtTokenProvider;
import com.project.common.dto.Group.Response.ResGroupDestinationDto;
import com.project.common.service.Group.GroupDestinationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/group")
@Api(tags = {"모임 목적지 API"})
public class GroupDestinationController {
    private final GroupDestinationService groupDestinationService;
    private final JwtTokenProvider jwtTokenProvider;
    
    @ApiOperation(value = "내 모임 목적지 조회")
    @GetMapping("/my-destination")
    public ResponseEntity<List<ResGroupDestinationDto>> getMyDestination(HttpServletRequest request) throws Exception{
   	 	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));
    	return new ResponseEntity<>(groupDestinationService.getMyDestination(userId),HttpStatus.OK);
    }
    
    //모임 목적지 조회
    @ApiOperation(value = "모임 목적지 조회")
    @GetMapping("/{groupSeq}/destination/list")
    public ResponseEntity<List<ResGroupDestinationDto>> getGroupDestination(@PathVariable("groupSeq") int groupSeq) throws Exception{
    	return new ResponseEntity<>(groupDestinationService.getGroupDestination(groupSeq),HttpStatus.OK);
    }

    //모임 목적지 추가
    @ApiOperation(value = "모임 목적지 추가")
    @PostMapping("/{groupSeq}/destination/add")
    public ResponseEntity<String> addGroupDestination(@PathVariable("groupSeq") int groupSeq,@RequestParam("heritageSeq") int heritageSeq){
    	return new ResponseEntity<>(groupDestinationService.addGroupDestination(groupSeq,heritageSeq),HttpStatus.CREATED);
    }

    
    //모임 목적지 삭제
   	@ApiOperation(value = "모임 목적지 삭제")
   	@DeleteMapping("/{groupSeq}/destination/delete")
   	public ResponseEntity<String> deleteGroupDestination(@PathVariable int groupSeq, @RequestParam("heritageSeq") int heritageSeq){
   		return new ResponseEntity<>(groupDestinationService.deleteGroupDestination(groupSeq, heritageSeq),HttpStatus.OK);
   	}
    
   	//모임 목적지 완료 표시
  	@ApiOperation(value = "모임 목적지 완료 표시 - gdCompleted N -> Y")
  	@PutMapping("/{groupSeq}/destination/complete")
  	public ResponseEntity<String> completeGroupDestination(@PathVariable int groupSeq,@RequestParam("heritageSeq") int heritageSeq){
  		return new ResponseEntity<>(groupDestinationService.completeGroupDestination(groupSeq,heritageSeq),HttpStatus.OK);
  	}
}
