package com.hyunjin.funding.dto.transaction;

import com.hyunjin.funding.domain.Transaction;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionInput {
  private String accountNumber;
  private String accountPassword;
  private long productId;

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
