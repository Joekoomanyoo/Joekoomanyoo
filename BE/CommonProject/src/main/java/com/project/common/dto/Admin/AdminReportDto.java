package com.project.common.dto.Admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminReportDto {
    private int reportSeq;
    private int reportType;
    private int reportTypeSeq;
    private String reportText;
    private char isSolved;
    private String userId;
    private String reportDate;
}
