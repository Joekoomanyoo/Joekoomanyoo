package com.project.common.dto.Feed;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReqFeedDto {
	
	// 피드 정보 //
    private String feedImgUrl;
   	private String feedTitle;
   	private String feedContent;
   	private char feedOpen;
	
   	// 해쉬태그 정보 //
   	List<String> hashtags;
    


}
