package com.sciencefl.flynn.exception;

import com.sciencefl.flynn.common.ResultCode;

public class DataAccessException extends BaseException {
    public DataAccessException() {
        super(ResultCode.DATABASE_ERROR);
    }

    public DataAccessException(String detail) {
        super(ResultCode.DATABASE_ERROR, detail);
    }

    public DataAccessException(Throwable cause) {
        super(ResultCode.DATABASE_ERROR, cause);
    }
}
