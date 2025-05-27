package com.sciencefl.flynn.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class ApiKeysConfig {

    List<ApiKeyModel> apiKeys;
}
