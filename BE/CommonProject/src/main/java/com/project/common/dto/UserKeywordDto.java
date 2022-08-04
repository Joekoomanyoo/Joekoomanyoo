package com.project.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserKeywordDto {
    private int myKeywordSeq;
    private int userSeq;
    private String myKeywordName;
    private LocalDateTime myKeywordRegistedAt;
}
