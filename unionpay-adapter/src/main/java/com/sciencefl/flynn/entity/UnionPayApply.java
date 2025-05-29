package com.sciencefl.flynn.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("union_pay_apply")
public class UnionPayApply {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String userId;

    private String couponId;

    private Integer ageRange;


    private String bankAccount;

    private BigDecimal amount;


    private String test;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
