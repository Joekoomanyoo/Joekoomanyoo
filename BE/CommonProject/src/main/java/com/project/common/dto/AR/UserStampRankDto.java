package com.project.common.dto.AR;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStampRankDto {
    private String userNickname;
    private int myStampCnt;
    private int userRank;
    private String profileImgUrl;
}
