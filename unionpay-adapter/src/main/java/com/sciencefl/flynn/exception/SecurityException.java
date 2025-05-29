package com.sciencefl.flynn.exception;


import com.sciencefl.flynn.common.ResultCode;

public class SecurityException extends BaseException {

    public SecurityException() {
        super(ResultCode.UNAUTHORIZED);
    }

    public SecurityException(ResultCode resultCode) {
        super(resultCode);
    }

    public SecurityException(ResultCode resultCode, Object... args) {
        super(resultCode, args);
    }
}
