package com.xiongus.bear.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractHttpException{

    public NotFoundException(String msg, Object... args) {
        super(msg, args);
        setHttpStatus(HttpStatus.NOT_FOUND);
    }
}
