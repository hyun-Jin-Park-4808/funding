package com.hyunjin.funding.dto;

import com.hyunjin.funding.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class SignUp {

    private String loginId;
    private String password;
    private List<String> roles;

    public User toEntity() {
      return User.builder()
          .loginId(this.loginId)
          .password(this.password)
          .roles(this.roles)
          .createdDate(LocalDateTime.now())
          .build();
    }
}
