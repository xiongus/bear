package com.xiongus.bear.console.utils;

import com.xiongus.bear.common.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;

public class RequestPrecondition {

    public static void checkArgumentsNotEmpty(String... args) {
        String CONTAIN_EMPTY_ARGUMENT = "request payload should not be contain empty.";
        checkArguments(!StringUtils.isAllEmpty(args), CONTAIN_EMPTY_ARGUMENT);
    }

    public static void checkModel(boolean valid){
        String ILLEGAL_MODEL = "request model is invalid";
        checkArguments(valid, ILLEGAL_MODEL);
    }

    public static void checkArguments(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new BadRequestException(String.valueOf(errorMessage));
        }
    }
}
