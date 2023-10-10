package com.hyunjin.funding.exception.impl;

import com.hyunjin.funding.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class NotFinishFundingException extends AbstractException {

  @Override
  public int getStatusCode() {
    return HttpStatus.BAD_REQUEST.value();
  }

  @Override
  public String getMessage() {
    return "아직 펀딩 진행중인 상품입니다.";
  }
}
