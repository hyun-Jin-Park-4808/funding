package com.hyunjin.funding.service;

import com.hyunjin.funding.domain.Transaction;
import com.hyunjin.funding.dto.transaction.FKInputForTransaction;
import com.hyunjin.funding.dto.transaction.TransactionInput;
import com.hyunjin.funding.exception.impl.AlreadyCancelFundingException;
import com.hyunjin.funding.exception.impl.AlreadyParticipatedInFundingException;
import com.hyunjin.funding.exception.impl.FailFundingException;
import com.hyunjin.funding.exception.impl.NotFinishFundingException;
import com.hyunjin.funding.exception.impl.ProductNotFoundException;
import com.hyunjin.funding.exception.impl.TransactionNotFoundException;
import com.hyunjin.funding.repository.MakerRepository;
import com.hyunjin.funding.repository.ProductRepository;
import com.hyunjin.funding.repository.TransactionRepository;
import com.hyunjin.funding.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class TransactionService {

  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final TransactionRepository transactionRepository;
  private final MakerRepository makerRepository;

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
      if (transaction.get().isParticipating()) { // 펀딩 참여 중복 검사
        throw new AlreadyParticipatedInFundingException();
      } else { // 과거 취소한 거래가 있으면 funding status만 true로 바꾸기
        transaction.get().setParticipating(true);
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

  public void updateSuccessRate(long productId) {
    var product = productRepository.findByProductId(productId);
    long price = product.get().getPrice();
    long successPrice = product.get().getSuccessPrice();
    long count = transactionRepository
        .countByProduct_ProductIdAndIsParticipating(productId, true);
    log.info("count: " + count);
    double successRateDouble = ((double) (price * count) / (double) (successPrice)) * 100.00;
    successRateDouble = Math.round(successRateDouble * 10000) / 10000.0;
    product.get().setSuccessRate(successRateDouble);
  }

  public Transaction fundingCancellation(String loginId, long productId) {
    var user = userRepository.findByLoginId(loginId);
    var product = productRepository.findByProductId(productId)
        .orElseThrow(() -> new ProductNotFoundException());

    var transaction = transactionRepository
        .findByUser_UserIdAndProduct_ProductId(user.get().getUserId(), productId)
        .orElseThrow(() -> new TransactionNotFoundException());

    if (!transaction.isParticipating()) { // 펀딩 취소 중복 검사
      throw new AlreadyCancelFundingException();
    }
    transaction.setParticipating(false); // 펀딩 상태 false로 바꾸기
    updateSuccessRate(productId); // 펀딩 성공률 계산

    return transaction;
  }

  public List<Transaction> payment(String loginId, long productId) {
    var user = userRepository.findByLoginId(loginId);
    var maker = makerRepository.findByUser_UserId(user.get().getUserId());
    var product = productRepository
        .findByProductIdAndMaker_MakerId(productId, maker.get().getMakerId())
        .orElseThrow(() -> new ProductNotFoundException());
    if (product.getEndDate().isAfter(LocalDateTime.now())) {
      throw new NotFinishFundingException();
    } else if (product.getSuccessRate() < 100.0) {
      throw new FailFundingException();
    }
    List<Transaction> transactions =
        transactionRepository.findAllByProductProductId(productId).get();
    for (Transaction transaction : transactions) {
      if (transaction.isParticipating()) {
        transaction.setPaid(true);
      }
    }
    return transactions;
  }
}
