package com.hyunjin.funding.repository;

import com.hyunjin.funding.domain.Transaction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  Optional<Transaction> findByUser_UserIdAndProduct_ProductId(long userId, long productId);
  Optional<List<Transaction>> findAllByProductProductId(long productId);
  long countByProduct_ProductIdAndIsParticipating(long productId, boolean trueCheck);
}
