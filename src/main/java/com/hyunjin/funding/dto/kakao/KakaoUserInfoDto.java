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
    @JsonProperty("kakao_account") // {"kakao_account" : ""} 형태의 JSON 데이터가 kakaoAccount 필드와 매핑된다.
    private KakaoAccount kakaoAccount;
}
