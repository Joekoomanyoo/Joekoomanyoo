package com.project.common.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

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
@Table(name = "tb_user")
// UserDetails는 시큐리티가 관리하는 객체
public class UserEntity implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userSeq;

    @Column(length = 225, nullable = false, unique = true)
    private String userId;

    @Column(length = 20, nullable = false, unique = true)
    private String userNickname;

    @Column(length = 300, nullable = false)
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

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.userPassword = passwordEncoder.encode(this.userPassword);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
