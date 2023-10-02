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

  @PostMapping("/register")
  @PreAuthorize("hasRole('MAKER')") // 헤더에 정보 등록
  public ResponseEntity<?> register(Principal principal, @RequestBody ProductInput productInput) {

    String userId = principal.getName();
    // 상품 등록
    var result = this.productService.register(userId, productInput);
    return ResponseEntity.ok(result);
  }
}
