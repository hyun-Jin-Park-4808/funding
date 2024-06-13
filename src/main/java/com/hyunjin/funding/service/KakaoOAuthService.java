package com.hyunjin.funding.service;

import com.hyunjin.funding.dto.kakao.*;
import com.hyunjin.funding.exception.impl.EmailNotFoundException;
import com.hyunjin.funding.exception.impl.KakaoAccessTokenFailedException;
import com.hyunjin.funding.repository.UserRepository;
import com.hyunjin.funding.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOAuthService {

  private final RestTemplate restTemplate;
  private final UserRepository userRepository;
  private final TokenProvider tokenProvider;

  @Value("${spring.security.oauth2.client.registration.kakao.client_id}")
  private String clientId;
  @Value("${spring.security.oauth2.client.registration.kakao.redirect_uri}")
  private String redirectUri;
  @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
  private String tokenUri;
  @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
  private String userInfoUri;
  @Value("${spring.security.oauth2.client.registration.kakao.client_secret}")
  private String clientSecret;
  private static final List<String> roles = List.of("ROLE_SUPPORTER");

  public KakaoSignIn kakaoOAuth(String code) {
    String kakaoAccessToken = getKakaoAccessToken(code);
    KakaoUserInfoDto kakaoUserInfoDto = getKakaoUserInfo(kakaoAccessToken);
    String email = kakaoUserInfoDto.getKakaoAccount().getEmail();
    boolean isAlreadyRegistered = userRepository.existsByLoginId(email);
    if(isAlreadyRegistered) {
      return kakaoSignIn(email);
    }
    return KakaoFirstSignInResponseDto.from(kakaoUserInfoDto);
  }

  private String getKakaoAccessToken(String code) { // 카카오 엑세스 토큰 요청
    ResponseEntity<KakaoTokenDto> response =
            restTemplate.exchange(
                    buildTokenUri(code),
                    HttpMethod.POST,
                    null,
                    KakaoTokenDto.class
            );
    // 응답 예외 처리
    if(response.getStatusCode() == HttpStatus.OK
            && response.getBody() != null) {
      return response.getBody().getAccessToken();
    } else {
      throw new KakaoAccessTokenFailedException();
    }
  }

  private String buildTokenUri(String code) {
    return UriComponentsBuilder.fromHttpUrl(tokenUri)
            .queryParam("grant_type", "authorization_code")
            .queryParam("client_id", clientId)
            .queryParam("redirect_uri", redirectUri)
            .queryParam("code", code)
            .queryParam("client_secret", clientSecret)
            .toUriString();
  }

  private KakaoUserInfoDto getKakaoUserInfo(String kakaoAccessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(kakaoAccessToken);
    HttpEntity<?> entity = new HttpEntity<>(headers);

    // 사용자의 정보 요청
    ResponseEntity<KakaoUserInfoDto> response = restTemplate.exchange(
            userInfoUri,
            HttpMethod.GET,
            entity,
            KakaoUserInfoDto.class
    );

    // 응답 예외 처리
    if(response.getStatusCode() == HttpStatus.OK
            && response.getBody() != null) {
      return response.getBody();
    } else {
      throw new KakaoAccessTokenFailedException();
    }
  }

  private KakaoSignInResponseDto kakaoSignIn(String email) {
    userRepository.findByLoginId(email)
            .orElseThrow(EmailNotFoundException::new);

    var accessToken = tokenProvider.generateToken(email, roles);
    return KakaoSignInResponseDto.builder()
            .accessToken(accessToken)
            .build();
  }
}