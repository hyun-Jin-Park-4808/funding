package com.hyunjin.funding.repository;

import com.hyunjin.funding.domain.Follow;
import com.hyunjin.funding.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {


  Optional<Follow> deleteByUser_UserIdAndMaker_MakerId(Long userId, long makerId);
}
