package com.project.common.entity.AR;

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
@Table(name = "tb_stamp")
public class StampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stampSeq;
    @Column(columnDefinition = "TEXT")
    private String stampImgUrl;
    @Column(nullable = false)
    private String stampTitle;
    @Column(nullable = false)
    private int heritageSeq;
    private String heritageLocal;
    private String heritageLng;
    private String heritageLat;
    @Column(nullable = false)
    private String stampCategory;
}
