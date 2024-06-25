package com.hyunjin.funding.dto.product;

import com.hyunjin.funding.domain.Product;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {

  private String companyName;
  private String productName;
  private String contents;
  private long price;
  private long successPrice;
  private long maxQuantity;
  private double successRate;
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public static ProductDetail fromEntity(Product product) {
    return ProductDetail.builder()
        .companyName(product.getCompanyName())
        .productName(product.getProductName())
        .contents(product.getContents())
        .price(product.getPrice())
        .successPrice(product.getSuccessPrice())
        .maxQuantity(product.getMaxQuantity())
        .successRate(product.getSuccessRate())
        .startDate(product.getStartDate())
        .endDate(product.getEndDate())
        .build();
  }
}
