package com.project.common.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.project.common.entity.GroupMemberEntity;


public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Long>,JpaSpecificationExecutor<GroupMemberEntity> {

	

    
}
