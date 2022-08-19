package com.project.common.repository.Group;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.common.entity.Group.GroupChatEntity;

public interface GroupChatRepository extends JpaRepository<GroupChatEntity, Integer> {

}
