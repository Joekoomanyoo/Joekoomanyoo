package com.project.common.repository.AR;

import com.project.common.entity.AR.StampCategoryEntity;
import com.project.common.entity.AR.StampEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampCategoryRepository extends JpaRepository<StampCategoryEntity,Integer> {
    StampCategoryEntity findByCategoryName(String category);

    StampCategoryEntity findByCategorySeq(int categorySeq);
}
