package com.project.common.dto.My;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyScheduleDto {
    private int myScheduleSeq;
    private int myScheduleDate;
    private String myScheduleContent;
    private String myScheduleRegistedAt;
    private String myScheduleUpdatedAt;
    private int userSeq;
}
