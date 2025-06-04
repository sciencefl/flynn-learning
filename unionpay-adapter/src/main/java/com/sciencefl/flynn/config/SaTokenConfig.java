package com.sciencefl.flynn.config;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Template;
import cn.dev33.satoken.oauth2.model.SaClientModel;
import com.sciencefl.flynn.dao.SaRedisDao;
import com.sciencefl.flynn.dao.entity.UnionPayOauthClient;
import com.sciencefl.flynn.service.OAuthClientService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class SaTokenConfig {

    /**
     * OAuth2 模板配置
     */
    @Bean
    public SaOAuth2Template saOAuth2Template(OAuthClientService clientService) {
        return new SaOAuth2Template() {
            @Override
            public SaClientModel getClientModel(String clientId) {
                UnionPayOauthClient client = clientService.getClientById(clientId);
                if(client == null || !client.getEnabled()) {
                    return null;
                }
                return new SaClientModel()
                        .setClientId(clientId)
                        .setClientSecret(client.getClientSecret())
                        .setAllowUrl("*")
                        .setContractScope(String.join(",", client.getScopes()))
                        .setIsAutoMode(true);
            }
        };
    }
    @Bean
    public SaTokenDao saTokenDao(@Qualifier("saTokenRedisTemplate")RedisTemplate<String, Object> redisTemplate) {
        return new SaRedisDao(redisTemplate);
    }
}