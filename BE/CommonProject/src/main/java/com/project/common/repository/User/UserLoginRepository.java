package com.project.common.repository.User;

import com.project.common.entity.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoginRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUserSeq(int userSeq);
}
