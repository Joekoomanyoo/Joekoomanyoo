package com.project.common.repository.Group;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.common.entity.Group.GroupDestinationEntity;


public interface GroupDestinationRepository extends JpaRepository<GroupDestinationEntity, Integer> {

	void deleteByGdSeq(int gdSeq);
}
