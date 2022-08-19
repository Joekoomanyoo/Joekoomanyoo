package com.project.common.controller.Feed;


import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.config.Jwt.JwtTokenProvider;
import com.project.common.service.Feed.FeedLikeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/feed/{feedSeq}/like")
@Api(tags = {"피드 좋아요 API"})
public class FeedLikeController {
    private final FeedLikeService feedLikeService;
    private final JwtTokenProvider jwtTokenProvider;
    
    @ApiOperation(value = "좋아요 갯수 조회")
    @GetMapping("/count")
    public ResponseEntity<Integer> getFeedLikeCount(@PathVariable("feedSeq") int feedSeq) throws Exception{
        return new ResponseEntity<>(feedLikeService.getFeedLikeCount(feedSeq),HttpStatus.OK);
    }
    
    @ApiOperation(value = "피드 좋아요 등록")
    @PostMapping("/add")
    public ResponseEntity<String> addFeedLike(HttpServletRequest request,@PathVariable("feedSeq") int feedSeq){
   	 	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));
         return new ResponseEntity<>(feedLikeService.addFeedLike(userId,feedSeq),HttpStatus.CREATED);
    }
    
   	@ApiOperation(value = "피드 좋아요 해제")
   	@DeleteMapping("/delete")
   	public ResponseEntity<String> deleteFeedLike(HttpServletRequest request,@PathVariable("feedSeq") int feedSeq){
   	 	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));
        return new ResponseEntity<>(feedLikeService.deleteFeedLike(userId,feedSeq),HttpStatus.OK);
   	}
  
}
