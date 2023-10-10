package com.hyunjin.funding.dto;

import com.hyunjin.funding.domain.Product;
import com.hyunjin.funding.domain.Transaction;
import com.hyunjin.funding.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FKInputForTransaction {

    private User user;
    private Product product;
}
