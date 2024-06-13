package com.hyunjin.funding.dto.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class KakaoUserInfoDto {
    // {"kakao_account" : "value"} 형태의 JSON 데이터가 KakaoUserInfoDto의 kakaoAccount 필드와 매핑된다.
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;
}
