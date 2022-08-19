package com.project.common.dto.Heritage;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HeritageDto {
    private int heritageSeq;
    private String heritageName;
    private String heritageEra;
    private String heritageAddress;
    private String heritageCategory;
    private String heritageLng;
    private String heritageLat;
    private String heritageImgUrl;
    private String heritageMemo;
    private String heritageVoice;
    private char stampExist;
    private String heritageClass;
    private int heritageScrapCnt;
    private int heritageReviewCnt;
    private String heritageLocal;
}
