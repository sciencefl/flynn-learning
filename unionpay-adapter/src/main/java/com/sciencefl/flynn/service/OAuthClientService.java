package com.sciencefl.flynn.service;

import cn.hutool.core.lang.UUID;
import com.sciencefl.flynn.dao.OauthClientDao;
import com.sciencefl.flynn.dao.entity.UnionPayOauthClient;
import com.sciencefl.flynn.exception.DataAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OAuthClientService {
    @Autowired
    private OauthClientDao oauthClientDao;

    @Autowired
    private OauthClientSecretEncoder secretEncoder;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CLIENT_CACHE_KEY = "oauth:client:";
    private static final long CACHE_DURATION = 3600; // 1小时

    public UnionPayOauthClient createClient(String clientName, List<String> scopes) {
        String clientId = generateClientId();
        String rawSecret = generateClientSecret();

        UnionPayOauthClient client = new UnionPayOauthClient();
        client.setClientId(clientId);
        client.setClientSecret(secretEncoder.encodeSecret(rawSecret));
        client.setClientName(clientName);
        client.setScopes(scopes);
        client.setEnabled(true);

        oauthClientDao.save(client);
        cacheClient(client);

        // 返回包含原始secret的临时对象
        client.setClientSecret(rawSecret);
        return client;
    }

    public boolean validateClient(String clientId, String clientSecret) {
        UnionPayOauthClient client = getClientById(clientId);
        if (client == null || !client.getEnabled()) {
            return false;
        }
        return secretEncoder.matches(clientSecret, client.getClientSecret());
    }

    private String generateClientId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private String generateClientSecret() {
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    private void cacheClient(UnionPayOauthClient client) {
        redisTemplate.opsForValue().set(
                CLIENT_CACHE_KEY + client.getClientId(),
                client,
                CACHE_DURATION,
                TimeUnit.SECONDS
        );
    }

    @Cacheable(cacheNames = "oauth_clients", key = "#clientId")
    public UnionPayOauthClient getClientById(String clientId) {
        try {
            return oauthClientDao.lambdaQuery()
                    .eq(UnionPayOauthClient::getClientId, clientId)
                    .one();
        } catch (Exception e) {
            throw new DataAccessException("获取客户端信息失败: " + e.getMessage());
        }
    }
}
