package com.sciencefl.flynn.exception;

import com.sciencefl.flynn.common.ResultCode;

public class ValidationException extends BaseException {

    public ValidationException(String message) {
        super(ResultCode.PARAM_ERROR, message);
    }

    public ValidationException(Object... args) {
        super(ResultCode.PARAM_ERROR, args);
    }
}