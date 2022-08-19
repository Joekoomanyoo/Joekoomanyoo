package com.project.common.repository.AR;

import com.project.common.entity.AR.MyStampEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyARRepository extends JpaRepository<MyStampEntity, Integer> {

    List<MyStampEntity> findAllByUserSeq(int userSeq);

}
