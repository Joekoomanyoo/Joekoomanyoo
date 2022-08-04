package com.project.common.repository;

import com.project.common.entity.UserKeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserKeywordRepository extends JpaRepository<UserKeywordEntity, Integer> {

    UserKeywordEntity findByUserSeq(int userSeq);
}
