package com.project.common.repository.Heritage;

import com.project.common.entity.Heritage.HeritageScrapEntity;

public interface HeritageScrapRepositoryCustom {
    boolean deleteByUserSeqAndHeritageSeq(int userSeq, int heritageSeq);
    HeritageScrapEntity findByUserSeqAndHeritageSeq(int userSeq, int heritageSeq);
}
