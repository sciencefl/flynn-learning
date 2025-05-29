package com.sciencefl.flynn.exception;

import com.sciencefl.flynn.common.ResultCode;
import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
    private final ResultCode resultCode;
    private final Object[] args;

    public BaseException(ResultCode resultCode, Object... args) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
        this.args = args;
    }

    public BaseException(ResultCode resultCode, Throwable cause) {
        super(resultCode.getMessage(), cause);
        this.resultCode = resultCode;
        this.args = new Object[0];
    }
}
