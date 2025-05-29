package com.sciencefl.flynn.exception;

import com.sciencefl.flynn.common.ResultCode;

public class BusinessException extends BaseException {
    public BusinessException(ResultCode resultCode) {
        super(resultCode);
    }

    public BusinessException(ResultCode resultCode, Object... args) {
        super(resultCode, args);
    }

    public BusinessException(ResultCode resultCode, Throwable cause) {
        super(resultCode, cause);
    }
}
