package com.project.common.repository.Feed;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.common.entity.Feed.FeedLikeEntity;


public interface FeedLikeRepository extends JpaRepository<FeedLikeEntity, Integer> {

	void deleteByFeedLikeSeq(int feedLikeSeq);

}
