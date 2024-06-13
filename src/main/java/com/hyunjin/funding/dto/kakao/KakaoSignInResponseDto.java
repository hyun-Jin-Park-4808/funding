package com.hyunjin.funding.dto.kakao;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoSignInResponseDto implements KakaoSignIn {
    private final Boolean registered = true;
    private String accessToken;
}
