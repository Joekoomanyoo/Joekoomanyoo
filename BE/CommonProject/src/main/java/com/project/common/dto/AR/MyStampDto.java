package com.project.common.dto.AR;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyStampDto {
    private int myStampSeq;
    private int stampSeq;
    private int userSeq;
    private int heritageSeq;
    private String myStampRegistedAt;
}
