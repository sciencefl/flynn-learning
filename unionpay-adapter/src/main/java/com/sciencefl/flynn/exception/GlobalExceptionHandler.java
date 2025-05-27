package com.sciencefl.flynn.exception;


import com.sciencefl.flynn.common.BaseResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice // 统一异常处理
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class) // 处理参数校验异常
    public BaseResponse<Void> handleValidationException(MethodArgumentNotValidException exception) {
        // 获取参数校验异常的错误信息
        String errorMessage = exception.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(","));
        // 返回错误信息
        return BaseResponse.failed(400,errorMessage);
    }
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<Void> handleBusinessException(BusinessException exception) {
        // 返回业务异常信息
        return BaseResponse.failed(exception.getCode(),  exception.getMessage());
    }
}
