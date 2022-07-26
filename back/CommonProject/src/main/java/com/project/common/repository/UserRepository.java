package com.project.common.repository;

import com.project.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// entity와 연결해서 repository 생성해주면 jpa가 생성된 것이다.
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUserId(String userId);

    Optional<UserEntity> findOneByUserId(String userId);
}
