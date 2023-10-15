package com.hyunjin.funding.repository;

import com.hyunjin.funding.domain.Product;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  boolean existsByProductName(String productName);

  Optional<Product> findByProductId(long id);
  Optional<Product> findByProductIdAndMaker_MakerId(long productId, long makerId);


  List<Product> findByStartDateAfterOrderByStartDateAsc(LocalDateTime today);
  List<Product> findByStartDateBeforeAndEndDateAfterOrderByStartDateAsc(LocalDateTime today1, LocalDateTime today2);
  List<Product> findByStartDateBeforeAndEndDateAfterOrderBySuccessRateDesc(LocalDateTime today1, LocalDateTime today2);
  List<Product> findByEndDateBeforeOrderByEndDateDesc(LocalDateTime today);

}
