package com.project.common.repository.AR;

import com.project.common.entity.AR.StampEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ARRepository extends JpaRepository<StampEntity, Integer> {

    StampEntity findByStampSeq(int stampSeq);
    List<StampEntity> findByHeritageLocal(String local);
    StampEntity findByHeritageSeq(int heritageSeq);
}
