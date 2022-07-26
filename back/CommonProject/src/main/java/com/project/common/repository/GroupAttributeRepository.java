package com.project.common.repository;


import com.project.common.entity.GroupAttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupAttributeRepository extends JpaRepository<GroupAttributeEntity, Long> {
	
  
    
}
