package com.project.common.service.Feed;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.entity.Feed.FeedEntity;
import com.project.common.entity.Feed.FeedLikeEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.repository.Feed.FeedLikeRepository;
import com.project.common.repository.Feed.FeedRepository;
import com.project.common.repository.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedLikeService {
	private final FeedRepository feedRepository;
	private final FeedLikeRepository feedLikeRepository;
	private final FeedService feedService;
	private final UserRepository userRepository;
	
	// 좋아요 갯수 조회
	public int getFeedLikeCount(int feedSeq) {
		int cnt=0;
		for (FeedLikeEntity entity : feedLikeRepository.findAll()) {
			if (entity.getFeed() != null && entity.getFeed().getFeedSeq() == feedSeq) {
				cnt++;
			}
		}
		return cnt;
	}

	// 피드 좋아요 등록
	@Transactional
	public String addFeedLike(String userId,int feedSeq) {
		UserEntity user = userRepository.findByUserId(userId);
		FeedEntity feed = feedService.findFeed(feedSeq);
		for (FeedLikeEntity entity : feed.getFeedLikes()) {
			if (entity.getUserSeq() == user.getUserSeq()) return "Fail - Already add";
		}
		feed.addFeedLike(FeedLikeEntity.builder().userSeq(user.getUserSeq()).build());
		feedRepository.save(feed);
		return "Success";
	}

	// 피드 좋아요 해제
	@Transactional
	public String deleteFeedLike(String userId,int feedSeq) {
		FeedEntity feed = feedService.findFeed(feedSeq);
		UserEntity user = userRepository.findByUserId(userId);
		int cnt =0;
		for (int i=0;i<feed.getFeedLikes().size();i++) {
			if (feed.getFeedLikes().get(i).getUserSeq() == user.getUserSeq()) {
				feedLikeRepository.deleteByFeedLikeSeq(feed.getFeedLikes().get(i).getFeedLikeSeq());
				feed.removeFeedLike(feed.getFeedLikes().get(i).getFeedLikeSeq());
				cnt++;
			}
		}
		if(cnt==0) 
			return "Fail - No like";
		return "Success";
	}

	// 피드 좋아요 리스트 찾기
	public List<FeedLikeEntity> findFeedLike(int feedSeq) {
		List<FeedLikeEntity> findFeedLike = new ArrayList<>();
		for (FeedLikeEntity entity : feedLikeRepository.findAll()) {
			if (entity.getFeed().getFeedSeq() == feedSeq) {
				findFeedLike.add(entity);
			}
		}
		return findFeedLike;
	}

}
