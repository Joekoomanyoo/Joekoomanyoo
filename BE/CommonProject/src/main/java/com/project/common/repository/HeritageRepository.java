package com.project.common.repository;

import com.project.common.entity.HeritageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeritageRepository extends JpaRepository<HeritageEntity, Integer> {

    List<HeritageEntity> findAll();
}
