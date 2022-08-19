package com.project.common.entity.Push;

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
@Getter
@Setter
@Table(name = "tb_push_history")
public class FcmHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pushSeq;
    @Column(nullable = false)
    private int userSeq;
    @Column(nullable = false)
    private String pushTitle;
    @Column(nullable = false)
    private String pushContent;
    @Column(nullable = false)
    private String pushCreatedAt;
}
