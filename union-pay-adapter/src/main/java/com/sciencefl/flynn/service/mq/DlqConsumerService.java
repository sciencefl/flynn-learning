package com.sciencefl.flynn.service.mq;

import com.alibaba.fastjson.JSON;
import com.sciencefl.flynn.common.message.BaseMessage;
import com.sciencefl.flynn.dto.ApplyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@RocketMQMessageListener(
        topic = "${rocketmq.dlq.topic}",
        consumerGroup = "${rocketmq.dlq.group}"
)
public class DlqConsumerService implements RocketMQListener<BaseMessage<ApplyDTO>> {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void onMessage(BaseMessage<ApplyDTO> message) {
        String messageId = message.getMessageId();
        String businessId = message.getBusinessId();

        log.error("消息进入死信队列: messageId={}, businessId={}, messageType={}",
                messageId, businessId, message.getMessageType());

        // 记录死信消息
        String dlqKey = String.format("mq:dlq:%s", messageId);
        redisTemplate.opsForValue().set(dlqKey, JSON.toJSONString(message), 7, TimeUnit.DAYS);

        // 告警通知
        // TODO: 实现告警通知逻辑
    }
}
