package com.hyunjin.funding.service;

import com.hyunjin.funding.domain.Product;
import com.hyunjin.funding.dto.product.ProductDetail;
import com.hyunjin.funding.dto.product.ProductInfo;
import com.hyunjin.funding.dto.product.ProductInput;
import com.hyunjin.funding.exception.impl.AlreadyExistUserException;
import com.hyunjin.funding.exception.impl.MakerNotFoundException;
import com.hyunjin.funding.exception.impl.ProductNotFoundException;
import com.hyunjin.funding.exception.impl.UserNotFoundException;
import com.hyunjin.funding.repository.MakerRepository;
import com.hyunjin.funding.repository.ProductRepository;
import com.hyunjin.funding.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

  private final ProductRepository productRepository;
  private final MakerRepository makerRepository;
  private final UserRepository userRepository;

  @Transactional
  public Product register(String loginId, ProductInput productInput) { // 제품 등록 메서드
    var user = userRepository.findByLoginId(loginId).orElseThrow(UserNotFoundException::new);
    var userId = user.getUserId();
    var maker = makerRepository.findByUser_UserId(userId).orElseThrow(MakerNotFoundException::new);
    boolean exists = this.productRepository.existsByProductName(productInput.getProductName());

    if (exists) { // 제품 이름 중복 검사
      throw new AlreadyExistUserException();
    }

      return this.productRepository.save(productInput.toEntity(maker));
  }

  public List<ProductInfo> getProductListScheduled() {
    return productRepository.findByStartDateAfterOrderByStartDateAsc(LocalDateTime.now())
        .stream().map((product)
            -> ProductInfo.builder()
            .companyName(product.getCompanyName())
            .productName(product.getProductName())
            .price(product.getPrice())
            .successRate(product.getSuccessRate())
            .startDate(product.getStartDate())
            .endDate(product.getEndDate())
            .build()).collect(Collectors.toList());
  }

  public List<ProductInfo> getProductListProceedingOrderByStartDt() { // 오름차순 정렬
    return productRepository
        .findByStartDateBeforeAndEndDateAfterOrderByStartDateAsc(LocalDateTime.now(), LocalDateTime.now())
        .stream().map((product)
            -> ProductInfo.builder()
            .companyName(product.getCompanyName())
            .productName(product.getProductName())
            .price(product.getPrice())
            .successRate(product.getSuccessRate())
            .startDate(product.getStartDate())
            .endDate(product.getEndDate())
            .build()).collect(Collectors.toList());
  }


  public List<ProductInfo> getProductListProceedingOrderBySuccessRate() { // 내림차순 정렬
    return productRepository
        .findByStartDateBeforeAndEndDateAfterOrderBySuccessRateDesc(LocalDateTime.now(), LocalDateTime.now())
        .stream().map((product)
            -> ProductInfo.builder()
            .companyName(product.getCompanyName())
            .productName(product.getProductName())
            .price(product.getPrice())
            .successRate(product.getSuccessRate())
            .startDate(product.getStartDate())
            .endDate(product.getEndDate())
            .build()).collect(Collectors.toList());
  }


  public List<ProductInfo> getProductListEnded() {
    return productRepository.findByEndDateBeforeOrderByEndDateDesc(LocalDateTime.now())
        .stream().map((product)
            -> ProductInfo.builder()
            .companyName(product.getCompanyName())
            .productName(product.getProductName())
            .price(product.getPrice())
            .successRate(product.getSuccessRate())
            .startDate(product.getStartDate())
            .endDate(product.getEndDate())
            .build()).collect(Collectors.toList());
  }

  public ProductDetail getDetails(long id) {

    var product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    return ProductDetail.fromEntity(product);
  }
}
