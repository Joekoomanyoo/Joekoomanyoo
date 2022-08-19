package com.project.common.dto.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseEvalDto {
    private int userSeq;
    private int evalCnt;
    private int evalList1;
    private int evalList2;
    private int evalList3;
    private int evalList4;
    private int evalList5;
}
