package com.project.common.dto.AR;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StampDto {
    private int stampSeq;
    private String stampImgUrl;
    private String stampTitle;
    private int heritageSeq;
    private String heritageLocal;
    private String heritageLng;
    private String heritageLat;
    private String stampCategory;
}
