package com.project.common.config.auth;

import com.project.common.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private static String secretKey = "myprojectsecret";
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    // 생명 주기 1시간
    private static long ACCESS_TOKEN_VALID_TIME = 60 * 60 * 1000L;

    // 생명 주기 1달
    private static long REFRESH_TOKEN_VALID_TIME = 30 * 24 * 60 * 60 * 1000L;


    private final UserDetailsService userDetailsService;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(int userSeq, String userId){
        Claims claims = Jwts.claims().setSubject(String.valueOf(userSeq)).setSubject(userId); // JWT payload 에 저장되는 정보단위
        Date now = new Date();
    // JWT Access 토큰 생성
        String accessToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

    // JWT Refresh 토큰 생성
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME))
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();
return "";
    }


    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }


    public int getUserSeq(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        String userSeq = String.valueOf(claims.getBody().get("userSeq"));
        return Integer.parseInt(userSeq);
    }


    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }


    // 토큰의 유효성 + 만료일자 확인
    public String validateRefreshToken(UserEntity userEntity) {

        String refreshToken = userEntity.getJwtToken();
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken);
        // 만약 refresh 토큰의 만료시간이 지나지 않은 경우 새로운 access 토큰 생성
            if(!claims.getBody().getExpiration().before(new Date())){
                return recreationAccessToken((Integer) claims.getBody().get("userSeq"), (String) claims.getBody().get("userId"));
            }
        } catch (Exception e) {
            // refresh 토큰이 만료되었을 경우, 로그인이 필요하다.
            return null;
        }
        return null;
    }

    // Access Token 재 생성 ( 만료되어서 )
    private String recreationAccessToken(int userSeq, String userId) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userSeq)).setSubject(userId); // JWT payload 에 저장되는 정보단위
        Date now = new Date();

        //Access Token
        String accessToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        return accessToken;
    }


}
