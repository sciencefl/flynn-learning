package com.sciencefl.flynn.aspect;


import cn.hutool.crypto.digest.DigestUtil;
import com.sciencefl.flynn.exception.AntiRePlayException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
public class GlobalAntiReplayAspect {
    @Autowired
    private RedisTemplate redisTemplate;

    @Around("@annotation(antiReplay)")
    public Object checkReplay(ProceedingJoinPoint joinPoint, AntiReplay antiReplay) throws Throwable {
        String key = antiReplay.keyPrefix() + generateRequestKey(joinPoint);
        long expireTime = antiReplay.expireTime();

        // 原子操作：仅当Key不存在时设置并设置过期时间
        Boolean isAbsent = redisTemplate.opsForValue().setIfAbsent(key, "1", expireTime, TimeUnit.SECONDS);

        if (Boolean.FALSE.equals(isAbsent)) {
            throw new AntiRePlayException("请求已处理，请勿重复提交");
        }
        return joinPoint.proceed();
    }

    private String generateRequestKey(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("Authorization");  // 用户令牌
        token = DigestUtil.md5Hex(token);
        String requestId = request.getHeader("X-Request-ID");  // 客户端生成唯一ID
        return token + ":" + requestId;  // 组合为Redis Key
    }
}
