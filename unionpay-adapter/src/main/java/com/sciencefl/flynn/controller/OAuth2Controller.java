package com.sciencefl.flynn.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.sciencefl.flynn.common.Result;
import com.sciencefl.flynn.common.ResultCode;
import com.sciencefl.flynn.dao.entity.UnionPayOauthClient;
import com.sciencefl.flynn.dto.CreateClientRequest;
import com.sciencefl.flynn.exception.SecurityException;
import com.sciencefl.flynn.service.OAuthClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
// @RequestMapping("/oauth2")
@RequestMapping("/api/v1/ssc/oauth2")
public class OAuth2Controller {
    @Autowired
    private OAuthClientService clientService;

    @PostMapping("/client")
    public Result<Map<String, String>> createClient(@RequestBody CreateClientRequest request) {
        UnionPayOauthClient client = clientService.createClient(
                request.getClientName(),
                request.getScopes()
        );

        Map<String, String> response = Map.of(
                "clientId", client.getClientId(),
                "clientSecret", client.getClientSecret() // 仅在创建时返回
        );

        log.info("Created new OAuth client: {}", client.getClientId());
        return Result.success(response);
    }

    @PostMapping("/token")
    public Result<Map<String, String>> getToken(
            @RequestParam String clientId,
            @RequestParam String clientSecret) {
        if (!clientService.validateClient(clientId, clientSecret)) {
            throw new SecurityException(ResultCode.UNAUTHORIZED, "Invalid client credentials");
        }

        StpUtil.login(clientId);

        // 获取token信息
        String tokenValue = StpUtil.getTokenValue();
        long timeout = StpUtil.getTokenTimeout();

        Map<String, String> response = Map.of(
                "access_token", tokenValue,
                "token_type", "Bearer",
                "expires_in", String.valueOf(timeout)
        );

        log.info("Generated JWT token for client: {}", clientId);
        return Result.success(response);
    }
}
