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
@Table(name = "tb_my_stamp")
public class MyStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int myStampSeq;

    @Column(nullable = false)
    private int stampSeq;

    @Column(nullable = false)
    private int userSeq;

    @Column(nullable = false)
    private int heritageSeq;

    @Column(nullable = false)
    private String myStampRegistedAt;
}
