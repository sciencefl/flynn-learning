package com.sciencefl.flynn.controller;


import com.sciencefl.flynn.common.BaseResponse;
import com.sciencefl.flynn.config.ApiKeyModel;
import com.sciencefl.flynn.config.ApiKeysConfig;
import com.sciencefl.flynn.config.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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

//    @Value("#{'${app.api-keys}'.split(',')}")
//    private List<String> validApiKeys;
    @Autowired
    private ApiKeysConfig apiModels;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/token")
    public BaseResponse<? extends Object> getToken(@RequestHeader("X-API-KEY") String apiKey) {
        // 验证API Key有效性（示例）
        Map<String, ApiKeyModel> apiKeyMap = apiModels.getApiKeys().stream().collect(Collectors.toMap(ApiKeyModel::getApiKey, apiKeyModel -> apiKeyModel));
        if (null == apiKeyMap.getOrDefault(apiKey,null)) {
            throw new AccessDeniedException("Invalid API Key");
        }
        log.info("请求apikey ：{}",apiKey);

        String token = jwtTokenProvider.generateToken(apiKeyMap.get(apiKey));
        return BaseResponse.success(Map.of(
                "access_token", token,
                "token_type", "Bearer",
                "expires_in", jwtTokenProvider.getExpirationMs() / 1000
        ));
    }
}
