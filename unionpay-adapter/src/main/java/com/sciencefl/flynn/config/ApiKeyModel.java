package com.sciencefl.flynn.config;

import lombok.Data;

import java.util.List;

@Data
public class ApiKeyModel {

    private String apiKey;
    private String appName;
    private List<String> scopes;
}
