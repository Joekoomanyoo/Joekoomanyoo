package com.project.common.repository;

import com.project.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoginRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUserSeq(int userSeq);
}
