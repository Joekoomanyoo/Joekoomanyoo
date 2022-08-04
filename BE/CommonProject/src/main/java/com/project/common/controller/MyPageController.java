package com.project.common.controller;

import com.project.common.dto.HeritageScrapDto;
import com.project.common.dto.UserKeywordDto;
import com.project.common.service.HeritageService;
import com.project.common.service.MyPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api("MyPageController")
@RequestMapping("/api/mypage")
public class MyPageController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final HeritageService heritageService;
    private final MyPageService myPageService;

    /**
     * 내 문화 유산 스크랩 목록 불러오기
     *
     * @param
     * @return List
     */

    @GetMapping("/scrap/{userSeq}")
    @ApiOperation(value = "내 문화 유산 스크랩 목록 불러오기", response = List.class)
    public ResponseEntity<?> myScrapList(@PathVariable("userSeq") int userSeq) {
        List<HeritageScrapDto> list = heritageService.myScrapList(userSeq);
        if (list == null) {
            return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    /**
     * 내 문화 유산 스크랩 삭제하기
     *
     * @param userSeq, heritageSeq
     * @return List
     */

    @DeleteMapping("/scrap/{userSeq}/{heritageSeq}")
    @ApiOperation(value = "내 문화 유산 스크랩 삭제하기", response = String.class)
    public ResponseEntity<String> deleteScrap(@PathVariable("userSeq") int userSeq, @PathVariable("heritageSeq") int heritageSeq) {
        if (heritageService.deleteScrap(userSeq, heritageSeq)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 내 키워드 생성하기
     *
     * @param userKeywordDto
     * @return String
     */

    @ApiOperation(value = "내 키워드 생성하기", response = String.class)
    @PostMapping("/keyword")
    public ResponseEntity<String> createKeyword(@RequestBody UserKeywordDto userKeywordDto) {
        if(myPageService.createKeyword(userKeywordDto)){
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(SUCCESS, HttpStatus.BAD_REQUEST);
        }
    }
}
