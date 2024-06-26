package com.hyunjin.funding.repository;

import com.hyunjin.funding.domain.Maker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MakerRepository extends JpaRepository<Maker, Long> {
  Optional<Maker> findByUser_UserId(long userId); // 외래키로 데이터 조회하기 위한 method 명 형식
  boolean existsByBusinessRegistrationNumber(String businessRegistrationNumber);
  boolean existsByPhone(String phone);
}
