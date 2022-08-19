package com.project.common.dto.AR;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyLocationDto {
    private int userSeq;
    private String lng;
    private String lat;
}
