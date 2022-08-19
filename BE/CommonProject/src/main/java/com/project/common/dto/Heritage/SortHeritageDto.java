package com.project.common.dto.Heritage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SortHeritageDto {
    private String lng;
    private String lat;
    private int categorySeq;
    private int sortSeq;
}
