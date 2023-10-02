package com.hyunjin.funding.dto;

import com.hyunjin.funding.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class MakerInput {
  private String businessRegistrationNumber;
  private String phone;
}
