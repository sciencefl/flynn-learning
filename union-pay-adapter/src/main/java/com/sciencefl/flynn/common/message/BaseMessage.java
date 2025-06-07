package com.sciencefl.flynn.common.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class BaseMessage<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    // 消息ID
    private String messageId;
    // 消息类型
    private String messageType;
    // 消息时间戳
    private LocalDateTime timestamp;
    // 消息来源
    private String source;
    // 业务ID
    private String businessId;
    // 消息追踪ID
    private String traceId;
    // 消息版本
    private String version;
    // 业务数据
    private T data;
}