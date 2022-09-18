package com.xiongus.bear.common.exception;

import com.google.common.base.Strings;
import java.io.Serial;
import org.springframework.http.HttpStatus;

public abstract class AbstractHttpException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 8094000382894904613L;

    protected HttpStatus httpStatus;

    public AbstractHttpException(String msg, Object... args){
        super(args == null || args.length == 0 ? msg : Strings.lenientFormat(msg, args));
    }

    public AbstractHttpException(String msg, Exception e){
        super(msg,e);
    }

    protected void setHttpStatus(HttpStatus httpStatus){
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
