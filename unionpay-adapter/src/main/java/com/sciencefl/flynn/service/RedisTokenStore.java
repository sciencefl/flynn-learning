package com.sciencefl.flynn.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Data
@Component
public class RedisTokenStore {
    private static final String TOKEN_PREFIX = "oauth2:token:";
    private static final String CLIENT_PREFIX = "oauth2:client:";
    private static final long TOKEN_DURATION = 7200; // 2小时

    @Autowired
    private final RedisTemplate<String, Object> redisTemplate;

    public void storeToken(String token, String clientId) {
        String key = TOKEN_PREFIX + token;
        redisTemplate.opsForValue().set(key, clientId, TOKEN_DURATION, TimeUnit.SECONDS);
    }

    public String getClientIdByToken(String token) {
        String key = TOKEN_PREFIX + token;
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void removeToken(String token) {
        String key = TOKEN_PREFIX + token;
        redisTemplate.delete(key);
    }
}
