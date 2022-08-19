package com.project.common.repository.Group;

import com.project.common.entity.Group.GroupMemberEntity;
import com.project.common.entity.Group.QGroupMemberEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupMemberRepositoryImpl implements GroupMemberRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public GroupMemberEntity findByUserSeqAndGroupSeq(int userSeq, int groupSeq) {
        QGroupMemberEntity qGroupMemberEntity = QGroupMemberEntity.groupMemberEntity;
        return jpaQueryFactory.select(qGroupMemberEntity).from(qGroupMemberEntity).where(qGroupMemberEntity.userSeq.eq(userSeq).and(qGroupMemberEntity.group.groupSeq.eq(groupSeq))).fetchOne();
    }
}
