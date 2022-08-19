package com.project.common.repository.User;

import com.project.common.entity.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEvalRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity getByUserSeq(int userSeq);

    UserEntity findByUserSeq(int userSeq);
}
