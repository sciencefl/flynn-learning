package com.sciencefl.flynn.exception;

import com.sciencefl.flynn.common.ResultCode;
import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
    private final ResultCode resultCode;
    private final String detail;
    private final Object data;

    protected BaseException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
        this.detail = null;
        this.data = null;
    }

    protected BaseException(ResultCode resultCode, String detail) {
        super(detail != null ? detail : resultCode.getMessage());
        this.resultCode = resultCode;
        this.detail = detail;
        this.data = null;
    }

    protected BaseException(ResultCode resultCode, String detail, Object data) {
        super(detail != null ? detail : resultCode.getMessage());
        this.resultCode = resultCode;
        this.detail = detail;
        this.data = data;
    }

    protected BaseException(ResultCode resultCode, Throwable cause) {
        super(resultCode.getMessage(), cause);
        this.resultCode = resultCode;
        this.detail = cause.getMessage();
        this.data = null;
    }
}
