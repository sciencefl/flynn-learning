package com.sciencefl.flynn.exception;

public class BusinessException extends RuntimeException {
    // 可选：定义错误码（根据业务需求）
    private int code = 500; // 默认500错误码

    // 基础构造器
    public BusinessException(String message) {
        super(message);
    }

    // 带错误码构造器
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    // 带原因链的构造器
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    // 完整参数构造器
    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    // Getter（根据需求）
    public int getCode() {
        return code;
    }
}
