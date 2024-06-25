package com.hyunjin.funding.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // api 접근 권한 설정 가능하도록 함.
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  private final JwtAuthenticationFilter authenticationFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .httpBasic().disable() // 기본 인증 로그인창 사용 안 함.
        .csrf().disable()
        .sessionManagement() // 세션 관리 추가
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // REST API로 JWT으로 토큰 인증방식 구현할 때 붙여줘야 하는 부분
        .and()
        .authorizeRequests()
        .antMatchers("/auth/**", "/product/list/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/api-docs/**").permitAll() // 모든 권한 허용
        .and()
        .addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class); // 필터 순서 정의
    //  UsernamePasswordAuthenticationFilter라는 기본 필터 앞에 authenticationFilter라는 커스텀 필터를 추가
  }

  @Bean
  @Override
  // AuthenticationManager: 인증 작업을 처리하는 역할
  // 이 인터페이스를 구현한 구현체는 사용자의 credential(주로 아이디, 패스워드)을 인증하고 Authentication 객체를 반환한다.
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean(); // 기본 인증 매니저 사용
  }
}
