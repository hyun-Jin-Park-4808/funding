package com.hyunjin.funding.dto;

import com.hyunjin.funding.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class SignIn {
    private String loginId;
    private String password;
}
