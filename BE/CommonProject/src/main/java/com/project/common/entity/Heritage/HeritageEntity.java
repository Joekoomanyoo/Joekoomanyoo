package com.project.common.entity.Heritage;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@ToString
@Data
// 파라미터가 없는 기본 생성자 생성
@NoArgsConstructor
// 모든 필드 값을 파라미터로 받는 생성자 생성
@AllArgsConstructor
@Table(name = "tb_heritage")
public class HeritageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int heritageSeq;

    @Column(length = 50, nullable = false)
    private String heritageName;

    @Column
    private String heritageEra;
    @Column(length = 50, nullable = false)
    private String heritageAddress;
    @Column
    private String heritageCategory;
    @Column(length = 30, nullable = false)
    private String heritageLng;
    @Column(length = 30, nullable = false)
    private String heritageLat;
    @Column
    private String heritageImgUrl;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String heritageMemo;
    @Column(columnDefinition = "TEXT")
    private String heritageVoice;
    @Column
    private char stampExist;
    @Column
    private String heritageClass;
    @Column
    private int heritageScrapCnt;
    @Column
    private int heritageReviewCnt;
    @Column
    private String heritageLocal;


}
