package com.sciencefl.flynn.service.mq;

import com.sciencefl.flynn.common.message.BaseMessage;
import com.sciencefl.flynn.dto.ApplyDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RocketMQMessageListener(topic ="TopicB",consumerGroup = "union-pay-consumer-group2",selectorExpression = "TagC")
public class ConsumerService2 implements RocketMQListener<BaseMessage<ApplyDTO>>, RocketMQPushConsumerLifecycleListener {

    @Override
    public void onMessage(BaseMessage<ApplyDTO> message) {
        log.info("ConsumerService2 接收到消息: {}", message.getMessageId());
    }
    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName("order-instance-" + UUID.randomUUID());
    }
}
