package com.hyunjin.funding.exception.impl;

import com.hyunjin.funding.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends AbstractException {

  @Override
  public int getStatusCode() {
    return HttpStatus.BAD_REQUEST.value();
  }

  @Override
  public String getMessage() {
    return "존재하지 않는 제품입니다.";
  }
}
