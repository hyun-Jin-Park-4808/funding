package com.hyunjin.funding.repository;

import com.hyunjin.funding.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByLoginId(String loginId);
  boolean existsByLoginId(String loginId);
}
