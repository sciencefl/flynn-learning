package com.sciencefl.flynn.controller;


import com.sciencefl.flynn.common.Result;
import com.sciencefl.flynn.common.ResultCode;
import com.sciencefl.flynn.config.ApiKeyModel;
import com.sciencefl.flynn.config.ApiKeysConfig;
import com.sciencefl.flynn.config.JwtTokenProvider;
import com.sciencefl.flynn.exception.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/ssc/auth")
public class AuthController {

    @Autowired
    private ApiKeysConfig apiModels;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/token")
    public Result<Map<String, Object>> getToken(@RequestHeader("X-API-KEY") String apiKey) {
        log.info("请求apikey：{}", apiKey);

        Map<String, ApiKeyModel> apiKeyMap = apiModels.getApiKeys().stream()
                .collect(Collectors.toMap(ApiKeyModel::getApiKey, apiKeyModel -> apiKeyModel));

        ApiKeyModel apiKeyModel = apiKeyMap.get(apiKey);
        if (apiKeyModel == null) {
            throw new SecurityException(ResultCode.UNAUTHORIZED, "无效的API密钥");
        }

        String token = jwtTokenProvider.generateToken(apiKeyModel);
        Map<String, Object> response = Map.of(
                "access_token", token,
                "token_type", "Bearer",
                "expires_in", jwtTokenProvider.getExpirationMs() / 1000
        );

        return Result.success(response);
    }
}
