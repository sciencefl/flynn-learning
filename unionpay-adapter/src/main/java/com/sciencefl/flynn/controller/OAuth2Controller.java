package com.sciencefl.flynn.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.sciencefl.flynn.common.Result;
import com.sciencefl.flynn.common.ResultCode;
import com.sciencefl.flynn.dao.entity.UnionPayOauthClient;
import com.sciencefl.flynn.dto.CreateClientRequest;
import com.sciencefl.flynn.dto.TokenRequest;
import com.sciencefl.flynn.exception.SecurityException;
import com.sciencefl.flynn.service.OAuthClientService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<Map<String, String>> getToken(@Valid @RequestBody TokenRequest request) {
        UnionPayOauthClient client = clientService.validateClientAndScope(request.getClientId(), request.getClientSecret());
        if (client == null) {
            throw new SecurityException(ResultCode.UNAUTHORIZED, "Invalid client");
        }
        String clientId = client.getClientId();

        // 创建session并登录
        StpUtil.login(clientId);

        // 在session中存储客户端信息
        StpUtil.getSessionByLoginId(clientId).set("client_info", client);
        StpUtil.getSessionByLoginId(clientId).set("scopes", client.getScopes());

        String tokenValue = StpUtil.getTokenValue();
        return Result.success(Map.of(
                "access_token", tokenValue,
                "token_type", "Bearer",
                "expires_in", String.valueOf(StpUtil.getTokenTimeout()),
                "scope", String.join(" ", client.getScopes())
        ));
    }
}
