package com.hyunjin.funding.controller;

import com.hyunjin.funding.Security.TokenProvider;
import com.hyunjin.funding.dto.Auth;
import com.hyunjin.funding.dto.MakerInput;
import com.hyunjin.funding.dto.ProductInput;
import com.hyunjin.funding.service.ProductService;
import com.hyunjin.funding.service.UserService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping("/register") // 제품 등록
  @PreAuthorize("hasRole('MAKER')") // 헤더에 정보 등록
  public ResponseEntity<?> register(Principal principal, @RequestBody ProductInput productInput) {

    String userId = principal.getName();
    // 상품 등록
    var result = this.productService.register(userId, productInput);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/list/scheduled") // 펀딩 진행 예정 상품 목록 조회
  public ResponseEntity<?> productListScheduled() {

    var result = productService.getProductListScheduled();

    return ResponseEntity.ok(result);
  }

  @GetMapping("/list/proceeding/start_date") // 펀딩 진행중 상품 목록 조회(시작날짜순 정렬)
  public ResponseEntity<?> productListProceedingOrderByStartDt() {

    var result = productService.getProductListProceedingOrderByStartDt();

    return ResponseEntity.ok(result);
  }


  @GetMapping("/list/proceeding/success_rate") // 펀딩 진행중 상품 목록 조회(펀딩 성공률순 정렬)
  public ResponseEntity<?> productListProceedingOrderBySuccessRate() {

    var result = productService.getProductListProceedingOrderBySuccessRate();

    return ResponseEntity.ok(result);
  }

  @GetMapping("/list/end")
  public ResponseEntity<?> productListEnded() { // 펀딩 진행 종료 상품 목록 조회(최근 종료 날짜순 정렬)

    var result = productService.getProductListEnded();

    return ResponseEntity.ok(result);
  }

  @GetMapping("/list/detail/{id}")
  public ResponseEntity<?> detail(@PathVariable(value = "id") long id) {

    var result = productService.getDetails(id);

    return ResponseEntity.ok(result);
  }

}
