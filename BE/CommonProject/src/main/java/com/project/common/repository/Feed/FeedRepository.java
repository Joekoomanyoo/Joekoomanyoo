package com.project.common.repository.Feed;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.common.entity.Feed.FeedEntity;

@Repository
public interface FeedRepository extends JpaRepository<FeedEntity, Integer> {

	FeedEntity findByFeedSeq(int feedSeq);

	void deleteByFeedSeq(int feedSeq);


}
