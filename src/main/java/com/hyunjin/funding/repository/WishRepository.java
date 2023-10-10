package com.hyunjin.funding.repository;

import com.hyunjin.funding.domain.User;
import com.hyunjin.funding.domain.Wish;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
  Optional<Wish> deleteByUser_UserIdAndProduct_ProductId(long userId, long productId);


}
