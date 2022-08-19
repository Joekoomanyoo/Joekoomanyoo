package com.project.common.repository.My;

import com.project.common.entity.My.MyScheduleEntity;

import java.util.List;

public interface MyScheduleRepositoryCustom {
	List<MyScheduleEntity> findByUserSeq(int userSeq);
}
