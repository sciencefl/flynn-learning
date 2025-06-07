package com.sciencefl.flynn.exception;

import com.sciencefl.flynn.common.ResultCode;

public class AntiRePlayException  extends BaseException {

    public AntiRePlayException() {
        super(ResultCode.ANTI_REPLAY_ERROR);
    }

    public AntiRePlayException(String detail) {
        super(ResultCode.ANTI_REPLAY_ERROR, detail);
    }

    public AntiRePlayException(String detail, Object data) {
        super(ResultCode.ANTI_REPLAY_ERROR, detail, data);
    }

    public AntiRePlayException(Throwable cause) {
        super(ResultCode.ANTI_REPLAY_ERROR, cause);
    }
}
