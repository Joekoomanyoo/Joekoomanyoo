package com.project.common.dto.Heritage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeritageReivewDto {
    private int heritageReviewSeq;
    private int userSeq;
    private int heritageSeq;
    private String userNickname;
    private String heritageReviewText;
    private String heritageReviewRegistedAt;
    private String reviewImgUrl;
    private String profileImgUrl;
}
