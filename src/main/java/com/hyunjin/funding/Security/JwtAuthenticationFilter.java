package com.hyunjin.funding.Security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/** api를 호출했을 때, 컨트롤러로 요청이 들어오기 전에 filter 를 먼저 거치고,
 그다음 servlet을 거치고, intercepter를 거치고,
 aop 레이어를 거친 다음에 컨트롤러쪽 코드가 실행이 된다.
 응답이 나갈때도 aop부터 역순으로 필터까지 거쳐서 나가게 된다.
 JWtAuthenticationFilter에서 OnceperRequestFilter를 정의해주면, 한 요청당 한 번씩 이 필터를 거치게 된다.
 요청이 들어올 때마다 토큰이 포함되었는 지를 확인해서 그 토큰이 유효한지 아닌지 확인해준다.
*/
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  
  private static final String TOKEN_HEADER = "Authorization"; // 토큰 주고받는 기준이 되는 키 값
  public static final String TOKEN_PREFIX = "Bearer"; // 인증 타입 나타내기 위함. JWT 토큰은 Bearer을 사용
  private final TokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token = this.resolveTokenFromRequest(request);

    if (StringUtils.hasText(token) && this.tokenProvider.validateToken(token)) {
      // 토큰 가지고 있으면 토큰 유효성 검증
      Authentication auth = this.tokenProvider.getAuthentication(token); // 토큰 인증 정보 가져옴.
      SecurityContextHolder.getContext().setAuthentication(auth); // 토큰 인증 정보 context에 넣어줌.
      // 어떤 사용자가 어떤 경로로 접근했는지 로그에 남김.
      log.info(String.format("[%s] -> %s", this.tokenProvider.getLoginId(token), request.getRequestURI()));
    }
    filterChain.doFilter(request, response); // 필터 연속 실행 가능하도록 함.
  }

  private String resolveTokenFromRequest(HttpServletRequest request) { // 토큰 존재하는 지 확인
    String token = request.getHeader(TOKEN_HEADER);

    if (!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)) { // 토큰 존재하고, prefix로 시작하면
      return token.substring(TOKEN_PREFIX.length()); // prefix 제외한 실제 토큰 부분 반환
    }
    return null; // 토큰 없으면 null 반환
  }
}
