package com.hyunjin.funding.repository;

import com.hyunjin.funding.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
  Optional<Follow> deleteByUser_UserIdAndMaker_MakerId(Long userId, long makerId);
}
