package com.hyunjin.funding.dto;

import com.hyunjin.funding.domain.User;
import lombok.Data;

public class Auth {

  @Data
  public static class SighIn { // 로그인
    private String loginId;
    private String password;
  }

  @Data
  public static class SighUp { // 회원가입
    private String loginId;
    private String password;

    public User toEntity() {
      return User.builder()
          .loginId(this.loginId)
          .password(this.password)
          .build();
    }
  }
}
