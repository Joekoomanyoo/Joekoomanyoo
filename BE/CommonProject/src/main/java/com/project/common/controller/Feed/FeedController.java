package com.project.common.controller.Feed;


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
import com.project.common.dto.Feed.FeedDto;
import com.project.common.dto.Feed.ReqFeedDto;
import com.project.common.dto.Feed.ResFeedDto;
import com.project.common.service.Feed.FeedService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/feed")
@Api(tags = {"피드 관리 API"})
public class FeedController {
    private final FeedService feedService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "피드 등록")
    @PostMapping("/add")
    public ResponseEntity<FeedDto> addFeed(HttpServletRequest request, @RequestBody ReqFeedDto feedDto){
   	 	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));
        return new ResponseEntity<>(feedService.addFeed(userId,feedDto), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "피드 전체 조회")
    @GetMapping("/list")
    public ResponseEntity<List<ResFeedDto>>getFeedList(HttpServletRequest request) throws Exception{
    	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));
    	return new ResponseEntity<>(feedService.getFeedList(userId),HttpStatus.OK);
    }
    
    @ApiOperation(value = "피드 조회 by 해쉬태그")
    @GetMapping("/list-by-hashtag/{fhTag}")
    public ResponseEntity<List<ResFeedDto>> getFeedListByTag(HttpServletRequest request,@PathVariable("fhTag") String fhTag) throws Exception{
     	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));
    	return new ResponseEntity<>(feedService.getFeedListByTag(fhTag,userId),HttpStatus.OK);
    }
    
    @ApiOperation(value = "내 피드 조회")
    @GetMapping("/my-feed")
    public ResponseEntity<List<ResFeedDto>> getMyFeedList(HttpServletRequest request) throws Exception{
    	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));
    	return new ResponseEntity<>(feedService.getMyFeedList(userId),HttpStatus.OK);
    }
    
    @ApiOperation(value = "피드 보기")
    @GetMapping("/{feedSeq}/info")
    public ResponseEntity<ResFeedDto> getFeedDetail(HttpServletRequest request,@PathVariable("feedSeq") int feedSeq){
     	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN")); 
    	return new ResponseEntity<>(feedService.getFeedDetail(feedSeq,userId),HttpStatus.OK);
    }
    
    @ApiOperation(value = "피드 삭제")
    @DeleteMapping("/{feedSeq}/delete")
    public ResponseEntity<String> deleteFeed(HttpServletRequest request,@PathVariable("feedSeq") int feedSeq){
   	 	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));
	     return new ResponseEntity<>(feedService.deleteFeed(userId,feedSeq),HttpStatus.OK);
    }
    
    @ApiOperation(value = "피드 수정")
    @PutMapping("/{feedSeq}/modify")
    public ResponseEntity<String> modifyFeed(@PathVariable("feedSeq") int feedSeq,@RequestBody ReqFeedDto feedDto){
	     return new ResponseEntity<>(feedService.modifyFeed(feedSeq,feedDto),HttpStatus.OK);
    }
    
    @ApiOperation(value = "피드 활성화 여부 - Y(공개), N(비공개)")
    @PutMapping("/{feedSeq}/active/{feedOpen}")
    public ResponseEntity<String> setFeedOpen(@PathVariable("feedSeq") int feedSeq, @PathVariable("feedOpen") char feedOpen){
	     return new ResponseEntity<>(feedService.setFeedOpen(feedSeq,feedOpen),HttpStatus.OK);
    } 

}
