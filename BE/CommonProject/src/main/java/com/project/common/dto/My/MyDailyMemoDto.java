package com.project.common.dto.My;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyDailyMemoDto {
    private int myDailyMemoSeq;
    private int myDailyMemoDate;
    private String myDailyMemoRegistedAt;
    private String myDailyMemoUpdatedAt;
    private String myDailyMemo;
    private int userSeq;
}
