package com.hyunjin.funding.service;

import com.hyunjin.funding.domain.Follow;
import com.hyunjin.funding.domain.Wish;
import com.hyunjin.funding.exception.impl.MakerNotFoundException;
import com.hyunjin.funding.exception.impl.ProductNotFoundException;
import com.hyunjin.funding.exception.impl.UserNotFoundException;
import com.hyunjin.funding.repository.FollowRepository;
import com.hyunjin.funding.repository.MakerRepository;
import com.hyunjin.funding.repository.ProductRepository;
import com.hyunjin.funding.repository.UserRepository;
import com.hyunjin.funding.repository.WishRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class UserService {

  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final MakerRepository makerRepository;
  private final WishRepository wishRepository;
  private final FollowRepository followRepository;


  public Wish wish(String loginId, long productId) {
    var user = userRepository.findByLoginId(loginId).orElseThrow(UserNotFoundException::new);
    var product = productRepository.findByProductId(productId).orElseThrow(ProductNotFoundException::new);
      return this.wishRepository.save(
        Wish.builder()
            .user(user)
            .product(product)
            .build());
  }

  public void deleteWish(String loginId, long productId) {
    var user = userRepository.findByLoginId(loginId);
    var userId = user.get().getUserId();
    wishRepository
        .deleteByUser_UserIdAndProduct_ProductId(userId, productId)
        .orElseThrow(ProductNotFoundException::new);
  }

  public Follow follow(String loginId, long makerId) {
    var user = userRepository.findByLoginId(loginId).orElseThrow(UserNotFoundException::new);
    var maker = makerRepository.findById(makerId).orElseThrow(MakerNotFoundException::new);
      return this.followRepository.save(
        Follow.builder()
            .user(user)
            .maker(maker)
            .build());
  }


  public void deleteFollow(String loginId, long makerId) {
    var user = userRepository.findByLoginId(loginId).orElseThrow(UserNotFoundException::new);
    var userId = user.getUserId();
    followRepository
        .deleteByUser_UserIdAndMaker_MakerId(userId, makerId)
        .orElseThrow(MakerNotFoundException::new);
  }
}
