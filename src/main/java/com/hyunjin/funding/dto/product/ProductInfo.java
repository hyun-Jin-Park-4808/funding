package com.hyunjin.funding.dto.product;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfo {
  private String companyName;
  private String productName;
  private long price;
  private double successRate;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
}
