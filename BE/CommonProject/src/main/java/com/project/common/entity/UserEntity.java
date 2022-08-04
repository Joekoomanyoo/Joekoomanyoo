package com.project.common.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    @Column(length = 50)
    private String fcmToken;

    private LocalDateTime userRegistedAt;

    private LocalDateTime userUpdatedAt;

    @Column(length = 1, nullable = false)
    private char isDeleted;

    private int evalCnt;
    private int evalList1;
    private int evalList2;
    private int evalList3;
    private int evalList4;
    private int evalList5;
    private LocalDateTime evalUpdatedAt;


    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.userPassword = passwordEncoder.encode(this.userPassword);
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
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
