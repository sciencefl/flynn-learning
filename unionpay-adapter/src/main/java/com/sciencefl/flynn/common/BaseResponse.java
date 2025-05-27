package com.sciencefl.flynn.common;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<T>(0, "success", data);
    }
    public static BaseResponse<Void> success() {
        return new BaseResponse<Void>(0, "success", null);
    }
    public static BaseResponse failed(int code, String message) {
        return new BaseResponse<>(code, message, null);
    }
}
