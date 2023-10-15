package com.hyunjin.funding.dto;

import com.hyunjin.funding.domain.Maker;
import com.hyunjin.funding.domain.Product;
import com.hyunjin.funding.domain.Transaction;
import com.hyunjin.funding.domain.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
public class TransactionInput {
  private String accountNumber;
  private String accountPassword;

  public Transaction toEntity(FKInputForTransaction fkInput) {
    return Transaction.builder()
        .accountNumber(this.accountNumber)
        .accountPassword(this.accountPassword)
        .isParticipating(true)
        .user(fkInput.getUser())
        .product(fkInput.getProduct())
        .build();
  }
}
