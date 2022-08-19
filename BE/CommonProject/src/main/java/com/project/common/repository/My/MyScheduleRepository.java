package com.project.common.repository.My;

import com.project.common.entity.My.MyScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyScheduleRepository extends JpaRepository<MyScheduleEntity, Integer> {

    MyScheduleEntity findByMyScheduleSeq(int myScheduleSeq);

    void deleteByMyScheduleSeq(int myScheduleSeq);

    void deleteAllByUserSeq(int userSeq);

}
