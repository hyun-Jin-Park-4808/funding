package com.hyunjin.funding.dto.product;

import com.hyunjin.funding.domain.Maker;
import com.hyunjin.funding.domain.Product;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInput {
  private String companyName;
  private String productName;
  private String contents;
  private Long price;
  private Long successPrice;
  private Long maxQuantity;
  private double successRate;
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public Product toEntity(Maker maker) {
    return Product.builder()
        .companyName(maker.getCompanyName())
        .productName(this.productName)
        .contents(this.contents)
        .price(this.price)
        .successPrice(this.successPrice)
        .maxQuantity(this.maxQuantity)
        .successRate(0.0)
        .startDate(this.startDate)
        .endDate(this.endDate)
        .maker(maker)
        .build();
  }
}
