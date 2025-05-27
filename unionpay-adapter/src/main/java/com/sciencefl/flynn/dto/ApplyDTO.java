package com.sciencefl.flynn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApplyDTO {
    @NotBlank(message = "用户唯一标识不能为空")
    @JsonProperty(value = "user_id")
    private String userId;

    @NotBlank(message = "消费券ID不能为空")
    @JsonProperty(value = "coupon_id")
    private String couponId;

    @Min(value = 0, message = "年龄范围不合法")
    @Max(value = 100)
    @JsonProperty(value = "age_range")
    private Integer ageRange;

    @JsonProperty(value = "bank_account")
    private String bankAccount;

    @DecimalMin("0.01")
    @JsonProperty(value = "amount")
    private BigDecimal amount;

    @JsonProperty(value = "test")
    private String test;

//    @JsonProperty(value = "expire_time")
//    @Future(message = "有效期必须是将来的时间")
//    private LocalDateTime expireTime;
}