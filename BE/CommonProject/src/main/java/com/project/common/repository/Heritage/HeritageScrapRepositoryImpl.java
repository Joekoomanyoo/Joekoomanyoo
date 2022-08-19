package com.project.common.repository.Heritage;

import com.project.common.entity.Heritage.HeritageScrapEntity;
import com.project.common.entity.Heritage.QHeritageScrapEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HeritageScrapRepositoryImpl implements HeritageScrapRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    // 삭제하기
    @Override
    public boolean deleteByUserSeqAndHeritageSeq(int userSeq, int heritageSeq) {
        QHeritageScrapEntity qHeritageScrapEntity = QHeritageScrapEntity.heritageScrapEntity;

        jpaQueryFactory.delete(qHeritageScrapEntity).where(qHeritageScrapEntity.userSeq.eq(userSeq), qHeritageScrapEntity.heritageSeq.eq(heritageSeq)).execute();
        return true;
    }

    // 찾기
    @Override
    public HeritageScrapEntity findByUserSeqAndHeritageSeq(int userSeq, int heritageSeq) {
        QHeritageScrapEntity qHeritageScrapEntity = QHeritageScrapEntity.heritageScrapEntity;
        return jpaQueryFactory.select(qHeritageScrapEntity).from(qHeritageScrapEntity).where(qHeritageScrapEntity.userSeq.eq(userSeq).and(qHeritageScrapEntity.heritageSeq.eq(heritageSeq))).fetchOne();
    }
}
