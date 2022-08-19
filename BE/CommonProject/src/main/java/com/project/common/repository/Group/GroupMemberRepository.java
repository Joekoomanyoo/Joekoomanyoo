package com.project.common.repository.Group;


import com.project.common.entity.Group.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.common.entity.Group.GroupMemberEntity;

import java.util.List;


public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Integer> {

	GroupMemberEntity findByUserSeq(int userSeq);

	void deleteByUserSeq(int userSeq);

	void deleteByMemberSeq(int memberSeq);

	List<GroupMemberEntity> findByGroup(GroupEntity groupEntity);
}
