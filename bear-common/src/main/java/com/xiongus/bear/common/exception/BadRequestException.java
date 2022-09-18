package com.xiongus.bear.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends AbstractHttpException{
    public BadRequestException(String msg, Object... args) {
        super(msg, args);
        setHttpStatus(HttpStatus.BAD_REQUEST);
    }
}
