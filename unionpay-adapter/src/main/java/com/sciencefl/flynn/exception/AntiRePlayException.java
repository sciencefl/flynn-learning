package com.sciencefl.flynn.exception;

import com.sciencefl.flynn.common.ResultCode;

public class AntiRePlayException  extends BaseException {

    public AntiRePlayException() {
        super(ResultCode.ANTI_REPLAY_ERROR);
    }

    public AntiRePlayException(Object... args) {
        super(ResultCode.ANTI_REPLAY_ERROR, args);
    }

    public AntiRePlayException(Throwable cause) {
        super(ResultCode.ANTI_REPLAY_ERROR, cause);
    }
}
