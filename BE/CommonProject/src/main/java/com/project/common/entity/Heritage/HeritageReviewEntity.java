package com.project.common.entity.Heritage;

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
@Table(name = "tb_heritage_review")
public class HeritageReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int heritageReviewSeq;
    @Column(nullable = false)
    private int userSeq;
    @Column(nullable = false)
    private int heritageSeq;
    @Column()
    private String userNickname;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String heritageReviewText;
    @Column(nullable = false)
    private String heritageReviewRegistedAt;
    private String reviewImgUrl;
    private String profileImgUrl;
}
