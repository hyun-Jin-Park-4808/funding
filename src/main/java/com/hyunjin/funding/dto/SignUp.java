package com.hyunjin.funding.dto;

import com.hyunjin.funding.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class SignUp {

    private String loginId;
    private String password;

    public User toEntity(List<String> rolesArr) {
      return User.builder()
          .loginId(this.loginId)
          .password(this.password)
          .roles(rolesArr)
          .createdDate(LocalDateTime.now())
          .build();
    }
}
