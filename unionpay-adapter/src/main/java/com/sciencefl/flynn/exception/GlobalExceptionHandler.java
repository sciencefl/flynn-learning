package com.sciencefl.flynn.exception;

import com.sciencefl.flynn.common.Result;
import com.sciencefl.flynn.common.ResultCode;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

    @Slf4j
    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(BusinessException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public Result<?> handleBusinessException(BusinessException ex) {
            log.error("业务异常: {}", ex.getMessage(), ex);
            return Result.error(ex.getResultCode(), ex.getMessage());
        }

        @ExceptionHandler(AntiRePlayException.class)
        @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
        public Result<?> handleAntiReplayException(AntiRePlayException ex) {
            log.warn("防重放异常: {}", ex.getMessage());
            return Result.error(ResultCode.ANTI_REPLAY_ERROR, ex.getMessage());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public Result<?> handleValidationException(MethodArgumentNotValidException ex) {
            String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            log.warn("参数验证失败: {}", errorMsg);
            return Result.error(ResultCode.PARAM_ERROR, errorMsg);
        }

        @ExceptionHandler(AccessDeniedException.class)
        @ResponseStatus(HttpStatus.FORBIDDEN)
        public Result<?> handleAccessDeniedException(AccessDeniedException ex) {
            log.warn("访问被拒绝: {}", ex.getMessage());
            return Result.error(ResultCode.FORBIDDEN);
        }

        @ExceptionHandler(AuthenticationException.class)
        @ResponseStatus(HttpStatus.UNAUTHORIZED)
        public Result<?> handleAuthenticationException(AuthenticationException ex) {
            log.warn("认证失败: {}", ex.getMessage());
            return Result.error(ResultCode.UNAUTHORIZED);
        }

        @ExceptionHandler(JwtException.class)
        @ResponseStatus(HttpStatus.UNAUTHORIZED)
        public Result<?> handleJwtException(JwtException ex) {
            log.warn("JWT令牌异常: {}", ex.getMessage());
            return Result.error(ResultCode.UNAUTHORIZED, "无效的认证令牌");
        }

        @ExceptionHandler(IllegalArgumentException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public Result<?> handleIllegalArgumentException(IllegalArgumentException ex) {
            log.warn("参数异常: {}", ex.getMessage());
            return Result.error(ResultCode.PARAM_ERROR, ex.getMessage());
        }

        @ExceptionHandler(Exception.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public Result<?> handleException(Exception ex) {
            log.error("系统异常: {}", ex.getMessage(), ex);
            return Result.error(ResultCode.INTERNAL_ERROR, "服务器内部错误");
        }
    }