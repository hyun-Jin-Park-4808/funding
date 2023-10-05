package com.hyunjin.funding.controller;

import com.hyunjin.funding.domain.Transaction;
import com.hyunjin.funding.dto.TransactionInput;
import com.hyunjin.funding.service.TransactionService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

  @PostMapping("/funding/{product_id}") // 펀딩 참여
  @PreAuthorize("hasRole('SUPPORTER')")
  public ResponseEntity<Transaction> fundingParticipation(Principal principal,
      @PathVariable(value = "product_id") long productId
      , @RequestBody TransactionInput transactionInput) {

    String loginId = principal.getName();

    var result = transactionService.fundingParticipation(loginId, productId, transactionInput);

    return ResponseEntity.ok(result);
  }

  @PostMapping("/funding-cancel/{product_id}") // 펀딩 취소
  @PreAuthorize("hasRole('SUPPORTER')")
  public ResponseEntity<Transaction> fundingCancellation(Principal principal,
      @PathVariable(value = "product_id") long productId) {

    String loginId = principal.getName();

    var result = transactionService.fundingCancellation(loginId, productId);

    return ResponseEntity.ok(result);
  }
}
