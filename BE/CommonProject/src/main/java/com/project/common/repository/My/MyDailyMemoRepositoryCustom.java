package com.project.common.repository.My;

import com.project.common.entity.My.MyDailyMemoEntity;

public interface MyDailyMemoRepositoryCustom {
    MyDailyMemoEntity findByUserSeqAndMyDailyMemoDate(int userSeq, int myDailyMemoDate);
}
