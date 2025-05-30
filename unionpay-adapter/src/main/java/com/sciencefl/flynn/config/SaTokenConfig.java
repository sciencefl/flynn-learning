package com.sciencefl.flynn.config;

import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Template;
import cn.dev33.satoken.oauth2.model.SaClientModel;
import cn.dev33.satoken.stp.StpLogic;
import com.sciencefl.flynn.service.OAuthClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SaTokenConfig {

    /**
     * JWT token 配置
     */
    @Bean
    @Primary
    public StpLogic getStpLogicJwt() {
        // 使用 JWT 模式
        return new StpLogicJwtForSimple();
    }

    /**
     * OAuth2 模板配置
     */
    @Bean
    public SaOAuth2Template saOAuth2Template(OAuthClientService clientService) {
        return new SaOAuth2Template() {
            @Override
            public SaClientModel getClientModel(String clientId) {
                // 通过 clientService 获取客户端信息
                return clientService.getClientById(clientId) != null ?
                        new SaClientModel()
                                .setClientId(clientId)
                                .setClientSecret(clientService.getClientById(clientId).getClientSecret())
                                .setAllowUrl("*")
                                .setContractScope("all")
                                .setIsAutoMode(true)
                        : null;
            }
        };
    }
}