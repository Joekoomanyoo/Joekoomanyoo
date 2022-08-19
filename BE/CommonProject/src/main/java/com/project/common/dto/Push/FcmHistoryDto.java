package com.project.common.dto.Push;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FcmHistoryDto {
    private int pushSeq;
    private int userSeq;
    private String pushTitle;
    private String pushContent;
    private String pushCreatedAt;
}
