-- ssc_ctid_oauth2_zfl.union_pay_oauth_client definition

CREATE TABLE `union_pay_oauth_client`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `client_id`     varchar(64)  NOT NULL COMMENT '客户端ID',
    `client_secret` varchar(255) NOT NULL COMMENT '客户端密钥',
    `client_name`   varchar(100) NOT NULL COMMENT '客户端名称',
    `scopes`        json                  DEFAULT NULL COMMENT '授权范围',
    `redirect_uri`  varchar(255)          DEFAULT NULL COMMENT '重定向URI',
    `enabled`       tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
    `deleted`       tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    `create_time`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_client_id` (`client_id`),
    KEY             `idx_create_time` (`create_time`),
    KEY             `idx_client_name` (`client_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='OAuth2客户端信息表';

CREATE TABLE `union_pay_apply`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`      varchar(64) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '用户唯一标识',
    `coupon_id`    varchar(64) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '消费券ID',
    `age_range`    int(11) DEFAULT NULL COMMENT '年龄范围',
    `bank_account` varchar(64) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '银行账户',
    `amount`       decimal(10, 2)                          DEFAULT NULL COMMENT '金额',
    `test`         varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '测试字段',
    `deleted`      tinyint(4) DEFAULT '0' COMMENT '逻辑删除标志：0-未删除，1-已删除',
    `create_time`  datetime                                DEFAULT NULL COMMENT '创建时间',
    `update_time`  datetime                                DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY            `idx_user_id` (`user_id`) COMMENT '用户ID索引'
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='银联支付申请表';