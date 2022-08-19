package com.project.common.repository.Heritage;

import com.project.common.entity.Heritage.HeritageScrapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeritageScrapRepository extends JpaRepository<HeritageScrapEntity, Integer> {

    List<HeritageScrapEntity> findAllByUserSeq(int userSeq);

    void deleteAllByUserSeq(int userSeq);
}
