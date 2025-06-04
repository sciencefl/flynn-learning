package com.sciencefl.flynn.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// 创建一个请求体DTO
@Data
public class TokenRequest {
    @NotBlank(message = "clientId不能为空")
    private String clientId;

    @NotBlank(message = "clientSecret不能为空")
    private String clientSecret;
}
