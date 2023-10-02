package com.hyunjin.funding.dto;

import com.hyunjin.funding.domain.Maker;
import com.hyunjin.funding.domain.Product;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProductInput {

  private String productName;
  private String contents;
  private Long price;
  private Long successPrice;
  private Long maxQuantity;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private Maker maker;

  public Product toEntity(Maker maker) {
    return Product.builder()
        .productName(this.productName)
        .contents(this.contents)
        .price(this.price)
        .successPrice(this.successPrice)
        .maxQuantity(this.maxQuantity)
        .startDate(this.startDate)
        .endDate(this.endDate)
        .maker(maker)
        .build();
  }
}
