package com.hyunjin.funding.exception.impl;

import com.hyunjin.funding.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class AlreadyCancelFundingException extends AbstractException {

  @Override
  public int getStatusCode() {
    return HttpStatus.BAD_REQUEST.value();
  }

  @Override
  public String getMessage() {
    return "이미 펀딩을 취소한 제품입니다.";
  }
}
