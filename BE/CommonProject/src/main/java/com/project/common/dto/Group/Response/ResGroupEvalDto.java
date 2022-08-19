package com.project.common.dto.Group.Response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResGroupEvalDto {
    private int userSeq;
    private int groupSeq;
    private String userNickname;
    private String profileImgUrl;
}
