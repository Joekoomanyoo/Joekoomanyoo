package com.project.common.dto.Push;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FcmRequestDto {
    private String targetToken;
    private String title;
    private String body;
}
