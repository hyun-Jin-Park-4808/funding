package com.hyunjin.funding.service;

import com.hyunjin.funding.domain.Transaction;
import com.hyunjin.funding.dto.FKInputForTransaction;
import com.hyunjin.funding.dto.TransactionInput;
import com.hyunjin.funding.exception.impl.AlreadyCancelFundingException;
import com.hyunjin.funding.exception.impl.AlreadyParticipatedInFundingException;
import com.hyunjin.funding.exception.impl.ProductNotFoundException;
import com.hyunjin.funding.exception.impl.TransactionNotFoundException;
import com.hyunjin.funding.repository.ProductRepository;
import com.hyunjin.funding.repository.TransactionRepository;
import com.hyunjin.funding.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {

  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final TransactionRepository transactionRepository;

  @Transactional
  public Transaction fundingParticipation(String loginId, long productId,
      TransactionInput transactionInput) {
    var user = userRepository.findByLoginId(loginId);
    var product = productRepository.findByProductId(productId)
        .orElseThrow(() -> new ProductNotFoundException());

    FKInputForTransaction fkInput = FKInputForTransaction.builder()
        .user(user.get()).product(product).build();

    // 이미 거래 존재하는지 검사
    var transaction = transactionRepository
        .findByUser_UserIdAndProduct_ProductId(user.get().getUserId(), productId);

    if (transaction.isPresent()) { // 기존 거래가 존재하는 경우
      if (transaction.get().isFundingStatus()) { // 펀딩 참여 중복 검사
        throw new AlreadyParticipatedInFundingException();
      } else { // 과거 취소한 거래가 있으면 funding status만 true로 바꾸기
        transaction.get().setFundingStatus(true);
        // Transactional로 관리하기 때문에 save 안 해도 true로 바뀜.
      }
    } else { // 기존 거래가 존재하지 않을 때
      Transaction result = this.transactionRepository
          .save(transactionInput.toEntity(fkInput));
      updateSuccessRate(productId); // 펀딩 성공률 계산

      return result;
    }
    updateSuccessRate(productId); // 펀딩 성공률 계산

    return transaction.get();
  }

  @Transactional
  public void updateSuccessRate(long productId) {
    var product = productRepository.findByProductId(productId);
    long price = product.get().getPrice();
    long successPrice = product.get().getSuccessPrice();
    long count = transactionRepository
        .countByProduct_ProductIdAndFundingStatus(productId, true);
    log.info("count: " + count);
    double successRateDouble = ((double) (price * count) / (double) (successPrice)) * 100.00;
    String successRate = String.format("%.4f", successRateDouble);
    product.get().setSuccessRate(successRate);
  }

  @Transactional
  public Transaction fundingCancellation(String loginId, long productId) {
    var user = userRepository.findByLoginId(loginId);
    var product = productRepository.findByProductId(productId)
        .orElseThrow(() -> new ProductNotFoundException());

    // 거래 존재하는지 검사
    var transaction = transactionRepository
        .findByUser_UserIdAndProduct_ProductId(user.get().getUserId(), productId);

    if (transaction.isPresent()) {
      if (!transaction.get().isFundingStatus()) { // 펀딩 취소 중복 검사
        throw new AlreadyCancelFundingException();
      } else { // 펀딩 상태 false로 바꾸기
        transaction.get().setFundingStatus(false);
      }
    } else { // 거래가 존재하지 않을 때 예외 처리
      throw new TransactionNotFoundException();
    }
    updateSuccessRate(productId); // 펀딩 성공률 계산

    return transaction.get();
  }
}
