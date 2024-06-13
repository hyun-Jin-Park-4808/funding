package com.hyunjin.funding.dto.kakao;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoFirstSignInResponseDto implements KakaoSignIn {
    private final Boolean registered = false;
    private String email;

    public static KakaoFirstSignInResponseDto from(KakaoUserInfoDto kakaoUserInfoDto) {
        return KakaoFirstSignInResponseDto.builder()
                .email(kakaoUserInfoDto.getKakaoAccount().getEmail())
                .build();
    }
}
