package com.project.common.repository.Group;

import com.project.common.entity.Group.GroupMemberEntity;

public interface GroupMemberRepositoryCustom {
    GroupMemberEntity findByUserSeqAndGroupSeq(int userSeq, int groupSeq);
}
