package com.hyunjin.funding.repository;

import com.hyunjin.funding.domain.Maker;
import com.hyunjin.funding.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakerRepository extends JpaRepository<Maker, Long> {

  Optional<Maker> findByBusinessRegistrationNumber(String businessRegistrationNumber);
  Optional<Maker> findByPhone(String phone);

  boolean existsByBusinessRegistrationNumber(String businessRegistrationNumber);
  boolean existsByPhone(String phone);

}
