package com.sciencefl.flynn.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.sciencefl.flynn.common.ResultCode;
import com.sciencefl.flynn.dao.entity.UnionPayOauthClient;
import com.sciencefl.flynn.exception.SecurityException;
import com.sciencefl.flynn.service.OAuthClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.TimeUnit;

@Component
public class OAuth2Interceptor implements HandlerInterceptor {
    @Autowired
    private OAuthClientService clientService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            throw new SecurityException(ResultCode.UNAUTHORIZED, "Missing token");
        }

        String token = auth.substring(7);

        String lockKey = "oauth2:lock:" + token;
        try {
            // 使用分布式锁防止并发访问
            boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, "1", 10, TimeUnit.SECONDS);
            if (!locked) {
                throw new SecurityException(ResultCode.UNAUTHORIZED, "Token is being processed");
            }
            // 验证token
            String clientId = (String) StpUtil.getLoginIdByToken(token);
            if (clientId == null) {
                throw new SecurityException(ResultCode.UNAUTHORIZED, "Invalid token");
            }

            UnionPayOauthClient client = clientService.getClientById(clientId);
            if (client == null || !client.getEnabled()) {
                throw new SecurityException(ResultCode.UNAUTHORIZED, "Invalid client");
            }

            return true;
        } finally {
            redisTemplate.delete(lockKey);
        }
    }
}
