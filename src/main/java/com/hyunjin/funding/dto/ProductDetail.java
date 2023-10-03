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
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {

  private String companyName;
  private String productName;
  private String contents;
  private Long price;
  private Long successPrice;
  private Long maxQuantity;
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
        .startDate(product.getStartDate())
        .endDate(product.getEndDate())
        .build();
  }
}
