package com.hyunjin.funding.security;

import com.hyunjin.funding.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class TokenProvider {

  private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1 hour
  private static final String KEY_ROLES = "roles";
  private final AuthService authService;

  @Value("{spring.jwt.secret}") // springframework에 있는 Value로 import!
  private String secretKey; // application.yml 파일에 secretKey 저장됨.

  public String generateToken(String loginId, List<String> roles) { // 토큰 생성
    Claims claims = Jwts.claims().setSubject(loginId); // 사용자 로그인 아이디 정보 저장
    claims.put(KEY_ROLES, roles); // 사용자 권한 정보 저장

    var now = new Date();
    var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

    return Jwts.builder()
        .setClaims(claims) // 사용자 정보
        .setIssuedAt(now) // 토큰 생성 시간
        .setExpiration(expiredDate) // 토큰 만료 시간
        .signWith(SignatureAlgorithm.HS256, this.secretKey) // 서명 생성
        .compact();
  }

  private Claims parseClaims(String token) { // 생성한 토큰으로부터 클레임 가져옴.
    try {
      return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

  public String getLoginId(String token) { // 토큰으로부터 로그인 아이디 가져옴.
    return this.parseClaims(token).getSubject();
  }

  // jwt를 스프링에서 지원해주는 토큰 형태로 바꿔서 사용자 정보와 권한 정보를 가져옴.
  public Authentication getAuthentication(String jwt) {
    UserDetails userDetails = this.authService.loadUserByUsername(this.getLoginId(jwt));
    return new UsernamePasswordAuthenticationToken(
        userDetails, "", userDetails.getAuthorities());
  }

  public boolean validateToken(String token) {
    if (!StringUtils.hasText(token)) {
      return false; // 토큰이 빈 값이면 유효하지 않으므로 false 반환
    }

    var claims = this.parseClaims(token);
    return !claims.getExpiration().before(new Date());
    // 토큰 만료시간이 현재 시간 이전이면 토큰 만료된 것이므로 false 반환
  }
}
