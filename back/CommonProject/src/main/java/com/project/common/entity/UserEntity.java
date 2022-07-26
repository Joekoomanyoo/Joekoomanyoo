package com.project.common.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@ToString
@Data
// 파라미터가 없는 기본 생성자 생성
@NoArgsConstructor
// 모든 필드 값을 파라미터로 받는 생성자 생성
@AllArgsConstructor
@Getter @Setter
@Table(name="tb_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userSeq;

    @Column(length = 225, nullable = false, unique = true)
    private String userId;

    @Column(length = 20, nullable = false, unique = true)
    private String userNickname;

    @Column(length = 100, nullable = false)
    private String userPassword;

    @Column(length = 10, nullable = false)
    private String userBirth;

    @Column(length = 10, nullable = false)
    private String socialLoginType;

    @Column(length = 1, nullable = false)
    private char userGender;

    @Column(length = 100)
    private String profileImgUrl;

    @Column(length = 50, nullable = false)
    private String jwtToken;

    @Column(length = 50)
    private String fcmToken;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date userRegistedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date userUpdatedAt;

    @Column(length = 1, nullable = false)
    private char isDeleted;

    @Column(length = 30, nullable = false)
    private String userLng;

    @Column(length = 30, nullable = false)
    private String userLat;


    public void encodePassword(PasswordEncoder passwordEncoder){
        this.userPassword = passwordEncoder.encode(this.userPassword);
    }
}
