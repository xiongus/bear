package com.xiongus.bear.common.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends AbstractHttpException {

  public ServiceException(String msg, Object... args) {
    super(msg, args);
    setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public ServiceException(String msg, Exception e) {
    super(msg, e);
    setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
