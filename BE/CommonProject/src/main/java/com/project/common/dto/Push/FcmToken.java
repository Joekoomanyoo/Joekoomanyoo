package com.project.common.dto.Push;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FcmToken {
    private int userSeq;
    private String fcmToken;
}
