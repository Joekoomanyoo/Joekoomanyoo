package com.project.common.entity.Admin;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Builder
@ToString
@Data
// 파라미터가 없는 기본 생성자 생성
@NoArgsConstructor
// 모든 필드 값을 파라미터로 받는 생성자 생성
@AllArgsConstructor
@Table(name = "tb_admin_report")
public class AdminReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportSeq;
    @Column(nullable = false)
    private int reportType;
    @Column(nullable = false)
    private int reportTypeSeq;
    private String reportText;
    @ColumnDefault("N")
    private char isSolved;
    @Column(nullable = false)
    private String userId;
    private String reportDate;

}
