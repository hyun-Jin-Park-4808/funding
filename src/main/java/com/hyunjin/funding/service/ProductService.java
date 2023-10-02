package com.hyunjin.funding.service;

import com.hyunjin.funding.domain.Maker;
import com.hyunjin.funding.domain.Product;
import com.hyunjin.funding.domain.User;
import com.hyunjin.funding.dto.Auth;
import com.hyunjin.funding.dto.ProductInput;
import com.hyunjin.funding.exception.impl.AlreadyExistUserException;
import com.hyunjin.funding.repository.MakerRepository;
import com.hyunjin.funding.repository.ProductRepository;
import com.hyunjin.funding.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final MakerRepository makerRepository;
  private final UserRepository userRepository;

  public Product register(String loginId, ProductInput productInput) { // 제품 등록 메서드
    var user = userRepository.findByLoginId(loginId);
    var userId = user.get().getUserId();
    var maker = makerRepository.findByUser_UserId(userId);
    boolean exists = this.productRepository.existsByProductName(productInput.getProductName());

    if (exists) { // 제품 이름 중복 검사
      throw new AlreadyExistUserException();
    }

    var result = this.productRepository.save(productInput.toEntity(maker.get()));
    return result;
  }
}
