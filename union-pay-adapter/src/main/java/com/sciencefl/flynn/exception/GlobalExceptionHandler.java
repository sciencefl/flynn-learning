package com.sciencefl.flynn.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.sciencefl.flynn.common.Result;
import com.sciencefl.flynn.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

    @Slf4j
    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(BaseException.class)
        public Result<?> handleBaseException(BaseException ex) {
            log.error("[{}] {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);
            return Result.error(ex.getResultCode(), ex.getDetail(), ex.getData());
        }

        @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public Result<?> handleValidationException(Exception ex) {
            Map<String, String> errors = new HashMap<>();
            if (ex instanceof MethodArgumentNotValidException) {
                ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors()
                        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            } else {
                ((BindException) ex).getBindingResult().getFieldErrors()
                        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            }

            String message = errors.entrySet().stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining("; "));

            log.warn("参数验证失败: {}", message);
            return Result.error(ResultCode.PARAM_ERROR, message, errors);
        }

        @ExceptionHandler({NotLoginException.class, NotPermissionException.class})
        @ResponseStatus(HttpStatus.UNAUTHORIZED)
        public Result<?> handleAuthException(Exception ex) {
            log.warn("认证/授权失败: {}", ex.getMessage());
            return Result.error(ResultCode.UNAUTHORIZED, ex.getMessage());
        }

        @ExceptionHandler(DataAccessException.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public Result<?> handleDataAccessException(DataAccessException ex) {
            log.error("数据库访问异常", ex);
            return Result.error(ResultCode.DATABASE_ERROR, "数据库访问异常");
        }

        @ExceptionHandler(Exception.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public Result<?> handleException(Exception ex) {
            log.error("系统异常", ex);
            return Result.error(ResultCode.INTERNAL_ERROR, "服务器内部错误");
        }
    }