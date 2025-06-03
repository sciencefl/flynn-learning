package com.sciencefl.flynn.common.message;

import cn.hutool.core.util.IdUtil;
import org.slf4j.MDC;

import java.time.LocalDateTime;

public class UnionPayMessageWrapper {

    public static <T> BaseMessage<T> build(T data, String messageType) {
        return new BaseMessage<T>()
                .setMessageId(IdUtil.fastSimpleUUID())
                .setMessageType(messageType)
                .setTimestamp(LocalDateTime.now())
                .setSource("union-pay-adapter")
                .setTraceId(MDC.get("traceId"))
                .setVersion("1.0")
                .setData(data);
    }
}