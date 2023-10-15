package com.hyunjin.funding.exception.impl;

import com.hyunjin.funding.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class FailFundingException extends AbstractException {

  @Override
  public int getStatusCode() {
    return HttpStatus.BAD_REQUEST.value();
  }

  @Override
  public String getMessage() {
    return "펀딩에 실패한 제품입니다.";
  }
}
