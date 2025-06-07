package com.sciencefl.flynn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * @author Flynn
 * @date 2023/10/17 16:14
 * @description 基础DTO
 */
@Data
public class BaseRequest {

    @NotBlank(message = "request_id不能为空")
    @JsonProperty(value = "request_id")
    private String requestId;

    @NotBlank(message = "operation不能为空")
    @JsonProperty(value = "operation")
    private String operation;

    @JsonProperty(value = "data")
    private List<Object> data;
}
