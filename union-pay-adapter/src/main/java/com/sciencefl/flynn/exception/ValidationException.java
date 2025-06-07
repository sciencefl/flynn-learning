package com.sciencefl.flynn.exception;

import com.sciencefl.flynn.common.ResultCode;

public class ValidationException extends BaseException {

    public ValidationException(String message) {
        super(ResultCode.PARAM_ERROR, message);
    }

    public ValidationException(String message, Object data) {
        super(ResultCode.PARAM_ERROR, message, data);
    }
}