package com.project.common.repository.AR;

import com.project.common.entity.AR.MyStampEntity;
import com.project.common.entity.AR.QMyStampEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ARRepositoryImpl implements ARRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public MyStampEntity findByUserSeqAndStampSeq(int userSeq, int stampSeq) {
        QMyStampEntity qMyStampEntity = QMyStampEntity.myStampEntity;
        return jpaQueryFactory.select(qMyStampEntity).from(qMyStampEntity).where(qMyStampEntity.userSeq.eq(userSeq).and(qMyStampEntity.stampSeq.eq(stampSeq))).fetchOne();
    }
}
