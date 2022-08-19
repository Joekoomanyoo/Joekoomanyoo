package com.project.common.dto.Feed;

import com.project.common.entity.Feed.FeedLikeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class FeedLikeDto {
    private int feedLikeSeq;
    private int userSeq;
    
    @Builder
    public FeedLikeDto(int feedLikeSeq, int userSeq) {
		super();
		this.feedLikeSeq = feedLikeSeq;
		this.userSeq = userSeq;
	}
	
    public FeedLikeEntity toEntity(){
        return FeedLikeEntity.builder()
                .feedLikeSeq(feedLikeSeq)
                .userSeq(userSeq)
                .build();
    }
}

