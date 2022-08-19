package com.project.common.repository.Push;

import com.project.common.entity.Push.FcmHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FcmHistoryRepository extends JpaRepository<FcmHistoryEntity, Integer> {

    List<FcmHistoryEntity> findAllByUserSeq(int userSeq);
}
