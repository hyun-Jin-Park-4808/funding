package com.hyunjin.funding.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PRODUCT")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private Long productId;

  @Column(name = "company_name")
  private String companyName;

  @Column(name = "product_name")
  private String productName;

  @Column(name = "contents")
  private String contents;

  @Column(name = "price")
  private Long price;

  @Column(name = "success_price")
  private Long successPrice;

  @Column(name = "max_quantity")
  private Long maxQuantity;

  @Column(name = "success_rate")
  private Double successRate;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @Column(name = "start_date")
  private LocalDateTime startDate;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @Column(name = "end_date")
  private LocalDateTime endDate;

  @JsonIgnore // 데이터를 front로 보낼 때 Json으로 변환 과정 중 무한으로 참조가 순환되어 발생하는 오류 방지
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn (name = "maker_id")
  private Maker maker;
}
