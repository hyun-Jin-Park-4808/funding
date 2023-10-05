package com.hyunjin.funding.repository;

import com.hyunjin.funding.domain.Maker;
import com.hyunjin.funding.domain.Product;
import com.hyunjin.funding.domain.Transaction;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  Optional<Transaction> findByUser_UserIdAndProduct_ProductId(long userId, long productId);

  long countByProduct_ProductIdAndFundingStatus(long productId, boolean trueCheck);
}
