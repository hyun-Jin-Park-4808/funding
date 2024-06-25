package com.hyunjin.funding.dto.transaction;

import com.hyunjin.funding.domain.Product;
import com.hyunjin.funding.domain.User;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FKInputForTransaction {
    private User user;
    private Product product;
}
