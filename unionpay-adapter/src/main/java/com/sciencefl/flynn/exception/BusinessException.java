package com.sciencefl.flynn.exception;

import com.sciencefl.flynn.common.ResultCode;

public class BusinessException extends BaseException {
    public BusinessException(ResultCode resultCode) {
        super(resultCode);
    }

    public BusinessException(ResultCode resultCode, String detail) {
        super(resultCode, detail);
    }

    public BusinessException(ResultCode resultCode, String detail, Object data) {
        super(resultCode, detail, data);
    }

    public BusinessException(ResultCode resultCode, Throwable cause) {
        super(resultCode, cause);
    }
}
