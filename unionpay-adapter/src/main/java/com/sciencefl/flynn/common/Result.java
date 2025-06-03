package com.sciencefl.flynn.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Result<T> {
    private Integer code;
    private String message;
    private String detail;
    private T data;
    private long timestamp;

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        result.setData(data);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }

    public static <T> Result<T> error(ResultCode resultCode) {
        return error(resultCode, null, null);
    }

    public static <T> Result<T> error(ResultCode resultCode, String detail) {
        return error(resultCode, detail, null);
    }

    public static <T> Result<T> error(ResultCode resultCode, String detail, T data) {
        Result<T> result = new Result<>();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        result.setDetail(detail);
        result.setData(data);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }
}