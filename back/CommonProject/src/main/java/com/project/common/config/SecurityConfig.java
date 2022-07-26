package com.project.common.config;

import com.project.common.config.auth.JwtAuthenticationFilter;
import com.project.common.service.auth.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
// spring security 설정 클래스임을 알려준다
@EnableWebSecurity
// customUserDetailsService 생성자 주입을 위한 lombok
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private CustomUserDetailService userDetailsService;

    private JwtEntryPoint jwtPoint;


    // 나중에 진짜로 인증하는 곳에서 사용한다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }



    // security 설정
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()  // 로그인경로 말고 다 권한 X
                .anyRequest().authenticated()   // 나머지는 권한이 있어야 접속 허용
                .and()
                .formLogin().loginPage("")  // 로그인 페이지 넣기
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtPoint)
                .and()
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .and()
                .headers()
                .frameOptions()
                .disable();
    }


    // 인증방법을 의미한다.
    // CustomUserDetailService 객체에 인증방법이 있으며, 패스워드 인코더 설정해놓음
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // 비밀번호가 DB에 BCrpyt로 저장되어 있는 것을 로그인 할 때 비교하기 쉽게 해주는 메서드
    // configure 메서드에 사용된다.
    // BCrpyt : 암호화 해시 함수
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}