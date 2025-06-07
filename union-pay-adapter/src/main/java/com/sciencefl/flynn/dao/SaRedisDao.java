package com.sciencefl.flynn.dao;


import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.session.SaSession;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SaRedisDao implements SaTokenDao {
    private final RedisTemplate<String, Object> redisTemplate;
    private final String keyPrefix = "sa-token:";

    public SaRedisDao(@Qualifier("saTokenRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(keyPrefix + key);
    }

    @Override
    public void set(String key, String value, long timeout) {
        if(timeout > 0) {
            redisTemplate.opsForValue().set(keyPrefix + key, value, timeout, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(keyPrefix + key, value);
        }
    }

    @Override
    public void update(String key, String value) {
        long expire = getTimeout(key);
        if(expire == NOT_VALUE_EXPIRE) {
            return;
        }
        this.set(key, value, expire);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(keyPrefix + key);
    }

    @Override
    public long getTimeout(String key) {
        Long expire = redisTemplate.getExpire(keyPrefix + key);
        return expire == null ? NOT_VALUE_EXPIRE : expire;
    }

    @Override
    public void updateTimeout(String key, long timeout) {
        if(timeout > 0) {
            redisTemplate.expire(keyPrefix + key, timeout, TimeUnit.SECONDS);
        }
    }

    @Override
    public Object getObject(String key) {
        // 当前系统未使用该功能，当前主要使用了 Sa-Token 的基本认证功能（OAuth2认证），并未使用到这些对象存储和搜索相关的方法
        return redisTemplate.opsForValue().get(keyPrefix + key);
    }

    @Override
    public void setObject(String key, Object value, long timeout) {
        // 当前系统未使用该功能，当前主要使用了 Sa-Token 的基本认证功能（OAuth2认证），并未使用到这些对象存储和搜索相关的方法
        if(timeout > 0) {
            redisTemplate.opsForValue().set(keyPrefix + key, value, timeout, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(keyPrefix + key, value);
        }
    }

    @Override
    public void updateObject(String s, Object o) {
        // 空实现 当前主要使用了 Sa-Token 的基本认证功能（OAuth2认证），并未使用到这些对象存储和搜索相关的方法
    }

    @Override
    public void deleteObject(String s) {
        // 空实现 当前主要使用了 Sa-Token 的基本认证功能（OAuth2认证），并未使用到这些对象存储和搜索相关的方法
    }

    @Override
    public long getObjectTimeout(String s) {
        return NOT_VALUE_EXPIRE; // 空实现 当前主要使用了 Sa-Token 的基本认证功能（OAuth2认证），并未使用到这些对象存储和搜索相关的方法
    }

    @Override
    public void updateObjectTimeout(String s, long l) {
        // 空实现 当前主要使用了 Sa-Token 的基本认证功能（OAuth2认证），并未使用到这些对象存储和搜索相关的方法
    }

    @Override
    public List<String> searchData(String s, String s1, int i, int i1, boolean b) {
        return List.of(); // 空实现 当前主要使用了 Sa-Token 的基本认证功能（OAuth2认证），并未使用到这些对象存储和搜索相关的方法
    }

    @Override
    public SaSession getSession(String sessionId) {
        try {
            Object sessionObj = this.getObject(sessionId);
            if (sessionObj == null) {
                return null;
            }

            // 如果是 LinkedHashMap，则需要转换
            if (sessionObj instanceof LinkedHashMap) {
                // 使用 BeanUtil 进行对象转换
                return BeanUtil.toBean(sessionObj, SaSession.class);
            }

            // 如果已经是 SaSession 类型
            if (sessionObj instanceof SaSession) {
                return (SaSession) sessionObj;
            }

            // 如果是字符串，尝试 JSON 转换
            if (sessionObj instanceof String) {
                return JSONUtil.toBean((String) sessionObj, SaSession.class);
            }

            // 其他情况，使用 Jackson 转换
            ObjectMapper mapper = new ObjectMapper();
            return mapper.convertValue(sessionObj, SaSession.class);

        } catch (Exception e) {
            log.error("Session反序列化失败: sessionId={}", sessionId, e);
            return null;
        }
    }
}

