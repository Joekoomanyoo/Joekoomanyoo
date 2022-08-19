package com.project.common.dto.AR;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyStampResponseDto {
    private String stampTitle;
    private String stampImgUrl;
    private String stampCategory;
}
