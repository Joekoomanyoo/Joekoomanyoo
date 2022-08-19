package com.project.common.dto.Feed;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.common.entity.Feed.FeedEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FeedDto {

	private int feedSeq;  
	
	// 모임 기본 정보 //
    private String feedImgUrl;
   	private String feedTitle;
   	private String feedContent;
   	private char feedOpen;

   
   	// 모임 설정 정보 //
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date createdTime;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date updatedTime;

	
	 private List<FeedLikeDto> feedLikes;
	 private List<FeedHashtagDto> hashtags ;
	
    @Builder
    public FeedDto(int feedSeq, String feedImgUrl, String feedTitle, String feedContent, char feedOpen,
    		Date createdTime, Date updatedTime) {
    	super();
    	this.feedSeq = feedSeq;
    	this.feedImgUrl = feedImgUrl;
    	this.feedTitle = feedTitle;
    	this.feedContent = feedContent;
    	this.feedOpen = feedOpen;
    	this.createdTime = createdTime;
    	this.updatedTime = updatedTime;
    }

    public FeedEntity toEntity(){
        return FeedEntity.builder()
                .feedSeq(feedSeq)
                .feedImgUrl(feedImgUrl)
                .feedTitle(feedTitle)
                .feedContent(feedContent)
                .feedOpen(feedOpen)
                .build();
    }



}
