package com.project.common.entity.Feed;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.project.common.entity.User.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder @AllArgsConstructor @NoArgsConstructor
@Entity
@Getter @Setter
@ToString
@Table(name="tb_feed")
public class FeedEntity {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="feed_seq")
	private int feedSeq;
    
	// 모임 기본 정보 //
	@Column(name="feed_title")
    private String feedTitle;
    
    @Column(name="feed_img_url")
    private String feedImgUrl;
 
    @Column(name="feed_content")
    private String feedContent;
    
    @Column(name="feed_open")
    private char feedOpen;
    
    // 모임 설정 정보 //
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="feed_created_at")
    private Date createdTime;
    
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="feed_updated_at")
    private Date updatedTime;

    @ManyToOne
	@JoinColumn(name="user_seq")
	private UserEntity user;
    
    // Feed Like
    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
    @Builder.Default
    private List<FeedLikeEntity> feedLikes = new ArrayList<>();
    
    // Feed Like Method 
    public void addFeedLike(FeedLikeEntity feedLike) {
    	this.feedLikes.add(feedLike);
    	feedLike.setFeed(this);
    }

    public void removeFeedLike(int feedLikeSeq) {
    	feedLikes.removeIf(feedLike ->
    		feedLike.getFeedLikeSeq()==feedLikeSeq);
    }
    
    // Feed HashTag
    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
    @Builder.Default
    private List<FeedHashtagEntity> hashtags = new ArrayList<>();
    
    // Feed HashTag Method
    public void addFeedHashtag(FeedHashtagEntity feedHashtag) {
    	this.hashtags.add(feedHashtag);
    	feedHashtag.setFeed(this);
    }

    public void removeFeedHashTag(int fhSeq) {
        this.hashtags.removeIf(feedHashtag ->
        feedHashtag.getFhSeq()==fhSeq);
    }
    
    
}
