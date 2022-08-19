package com.project.common.repository.My;

import com.project.common.entity.My.MyDailyMemoEntity;
import com.project.common.entity.My.QMyDailyMemoEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyDailyMemoRepositoryImpl implements MyDailyMemoRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public MyDailyMemoEntity findByUserSeqAndMyDailyMemoDate(int userSeq, int myDailyMemoDate) {
        QMyDailyMemoEntity qMyDailyMemoEntity = QMyDailyMemoEntity.myDailyMemoEntity;

        return jpaQueryFactory.select(qMyDailyMemoEntity).from(qMyDailyMemoEntity).where(qMyDailyMemoEntity.userSeq.eq(userSeq).and(qMyDailyMemoEntity.myDailyMemoDate.eq(myDailyMemoDate))).fetchOne();
    }
}
