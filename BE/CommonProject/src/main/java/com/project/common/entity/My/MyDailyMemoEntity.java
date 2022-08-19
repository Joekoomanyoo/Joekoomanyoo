package com.project.common.entity.My;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@ToString
@Data
// 파라미터가 없는 기본 생성자 생성
@NoArgsConstructor
// 모든 필드 값을 파라미터로 받는 생성자 생성
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tb_daily_memo")
public class MyDailyMemoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int myDailyMemoSeq;
    @Column(nullable = false)
    private int myDailyMemoDate;

    private String myDailyMemoRegistedAt;

    private String myDailyMemoUpdatedAt;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String myDailyMemo;
    @Column(nullable = false)
    private int userSeq;
}
