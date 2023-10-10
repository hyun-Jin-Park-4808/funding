package com.hyunjin.funding.dto;

import com.hyunjin.funding.domain.Maker;
import com.hyunjin.funding.domain.Product;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfo {

  private String companyName;
  private String productName;
  private long price;
  private String successRate;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
}
