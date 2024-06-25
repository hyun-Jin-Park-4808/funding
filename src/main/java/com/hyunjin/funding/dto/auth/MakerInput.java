package com.hyunjin.funding.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MakerInput {
  private String companyName;
  private String businessRegistrationNumber;
  private String phone;
}
