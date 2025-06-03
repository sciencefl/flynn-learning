package com.sciencefl.flynn.exception;

import com.sciencefl.flynn.common.ResultCode;

public class RedisException extends BaseException {
    public RedisException() {
        super(ResultCode.REDIS_ERROR);
    }

    public RedisException(String detail) {
        super(ResultCode.REDIS_ERROR, detail);
    }

    public RedisException(Throwable cause) {
        super(ResultCode.REDIS_ERROR, cause);
    }
}