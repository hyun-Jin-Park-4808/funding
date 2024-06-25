package com.hyunjin.funding.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignIn {
    private String loginId;
    private String password;
}
