package com.project.common.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.common.entity.Feed.FeedEntity;
import com.project.common.entity.Group.GroupEntity;

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

    @Column(length = 250)
    private String profileImgUrl;

    @Column(length = 1000)
    private String fcmToken;

    private String userRegistedAt;

    private String userUpdatedAt;

    @Column(length = 1, nullable = false)
    private char isDeleted;
    
    private int evalCnt;
    private int evalList1;
    private int evalList2;
    private int evalList3;
    private int evalList4;
    private int evalList5;
    private String evalUpdatedAt;
    @Column(nullable = false)
    @ColumnDefault("Y")
    private char pushSettingStatus;
    @ColumnDefault("0")
    private int myStampCnt;

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
    

    // 모임 추가 //
    @Builder.Default @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<GroupEntity> groups = new ArrayList<>();
    
    // 모임 추가 Method //
    public void addGroup(GroupEntity group) {
    	this.groups.add(group);
    	group.setUser(this);
    }

    public void removeGroup(int groupSeq) {
        this.groups.removeIf(group -> group.getGroupSeq()==groupSeq);
    }
    
    // 피드 추가 //
    @Builder.Default @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FeedEntity> feeds = new ArrayList<>();
    
    // 모임 추가 Method //
    public void addFeed(FeedEntity feed) {
    	this.feeds.add(feed);
    	feed.setUser(this);
    }

    public void removeFeed(int feedSeq) {
        this.feeds.removeIf(feed -> feed.getFeedSeq()==feedSeq);
    }

}
