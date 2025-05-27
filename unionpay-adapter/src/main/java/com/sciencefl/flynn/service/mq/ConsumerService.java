package com.sciencefl.flynn.service.mq;

import cn.hutool.json.JSONUtil;
import com.sciencefl.flynn.dto.ApplyDTO;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RocketMQMessageListener(topic ="TopicB",consumerGroup = "union-pay-consumer-group",selectorExpression = "TagB")
public class ConsumerService implements RocketMQListener<ApplyDTO> , RocketMQPushConsumerLifecycleListener {
    @Override
    public void onMessage(ApplyDTO applyDTO) {
        JSONUtil.toJsonStr(applyDTO);
        System.out.println(JSONUtil.toJsonStr(applyDTO));
    }
    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName("order-instance-" + UUID.randomUUID());
    }
}
