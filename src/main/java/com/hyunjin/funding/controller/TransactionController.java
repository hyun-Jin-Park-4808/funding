package com.hyunjin.funding.controller;

import com.hyunjin.funding.domain.Transaction;
import com.hyunjin.funding.dto.TransactionInput;
import com.hyunjin.funding.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

  private final TransactionService transactionService;

  @ApiOperation(value = "펀딩에 참여하기 위한 api입니다.\n "
      + "토큰 정보가 필요합니다.")
  @PostMapping("/funding/{product_id}") // 펀딩 참여
  @PreAuthorize("hasRole('SUPPORTER')")
  public ResponseEntity<Transaction> fundingParticipation(Principal principal,
      @PathVariable(value = "product_id") long productId
      , @RequestBody TransactionInput transactionInput) {

    String loginId = principal.getName();

    var result = transactionService.fundingParticipation(loginId, productId, transactionInput);

    return ResponseEntity.ok(result);
  }

  @ApiOperation(value = "펀딩 취소를 위한 api입니다.\n "
      + "토큰 정보가 필요합니다.")
  @DeleteMapping("/funding/{product_id}") // 펀딩 취소
  @PreAuthorize("hasRole('SUPPORTER')")
  public ResponseEntity<Transaction> fundingCancellation(Principal principal,
      @PathVariable(value = "product_id") long productId) {

    String loginId = principal.getName();

    var result = transactionService.fundingCancellation(loginId, productId);

    return ResponseEntity.ok(result);
  }

  /**
   * 결제 api(실제 결제 시스템 도입은 금액 지불 문제 등이 있어 결제 상태만 바꾸는 수준의 Sutb API로 구성)
   */
  @ApiOperation(value = "결제를 진행하기 위한 Stub api입니다.\n "
      + "MAKER 권한을 가진 사용자의 토큰 정보가 필요합니다.")
  @PostMapping("/payment/{product_id}")
  @PreAuthorize("hasRole('MAKER')")
  public ResponseEntity<List<Transaction>> payment(Principal principal,
      @PathVariable(value = "product_id") long productId) {

    String loginId = principal.getName();

    var result = transactionService.payment(loginId, productId);

    return ResponseEntity.ok(result);
  }
}
