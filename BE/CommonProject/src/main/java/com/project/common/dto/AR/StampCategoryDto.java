package com.project.common.dto.AR;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StampCategoryDto {
    private int categorySeq;
    private String categoryName;
    private int categoryCnt;
}
