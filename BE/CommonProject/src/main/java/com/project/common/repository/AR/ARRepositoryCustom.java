package com.project.common.repository.AR;

import com.project.common.entity.AR.MyStampEntity;

public interface ARRepositoryCustom {
    MyStampEntity findByUserSeqAndStampSeq(int userSeq, int stampSeq);
}
