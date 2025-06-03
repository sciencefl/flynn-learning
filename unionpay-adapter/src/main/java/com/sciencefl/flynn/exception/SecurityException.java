package com.sciencefl.flynn.exception;


import com.sciencefl.flynn.common.ResultCode;

public class SecurityException extends BaseException {

    public SecurityException() {
        super(ResultCode.UNAUTHORIZED);
    }

    public SecurityException(ResultCode resultCode) {
        super(resultCode);
    }

    public SecurityException(ResultCode resultCode, String detail) {
        super(resultCode, detail);
    }

    public SecurityException(ResultCode resultCode, String detail, Object data) {
        super(resultCode, detail, data);
    }

    public SecurityException(ResultCode resultCode, Throwable cause) {
        super(resultCode, cause);
    }
}
