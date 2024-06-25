package com.hyunjin.funding.dto.transaction;

import com.hyunjin.funding.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInput {
  private long productId;
}
