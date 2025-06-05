package com.sciencefl.flynn.interceptor;

import cn.hutool.core.lang.UUID;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    public static final String TRACE_ID = "traceId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceId = UUID.fastUUID().toString().substring(0,16); // 生成16位的UUID作为traceId
        MDC.put(TRACE_ID, traceId);  // 存入 MDC
        log.info("[API] Request started - method:{}, uri:{}, clientIp:{}, traceId:{}", request.getMethod(), request.getRequestURI(), request.getRemoteAddr(), traceId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.remove(TRACE_ID);  // 请求完成后清理 MDC
    }
}
