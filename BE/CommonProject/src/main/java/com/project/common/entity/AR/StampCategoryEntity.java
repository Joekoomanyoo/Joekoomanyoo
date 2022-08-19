package com.project.common.entity.AR;

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
@Table(name = "tb_stamp_category")
public class StampCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categorySeq;
    @Column(nullable = false)
    private String categoryName;
    @Column(nullable = false)
    @ColumnDefault("0")
    private int categoryCnt;
}
