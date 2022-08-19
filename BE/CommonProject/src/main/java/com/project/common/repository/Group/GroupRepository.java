package com.project.common.repository.Group;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.common.entity.Group.GroupEntity;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {

	GroupEntity findByGroupSeq(int groupSeq);


}
