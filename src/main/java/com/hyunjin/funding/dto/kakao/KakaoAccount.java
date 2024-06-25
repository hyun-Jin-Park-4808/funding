package com.hyunjin.funding.dto.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
// JSON 데이터에 Java 객체의 필드에 매핑되지 않는 속성이 있더라도 예외 발생시키지 않고 해당 프로퍼티를 무시함.
@AllArgsConstructor
@NoArgsConstructor
public class KakaoAccount {
    private String email;
}
