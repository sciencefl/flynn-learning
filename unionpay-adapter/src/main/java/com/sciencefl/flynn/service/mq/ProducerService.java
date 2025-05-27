package com.sciencefl.flynn.service.mq;

import com.sciencefl.flynn.dto.ApplyDTO;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Autowired
    RocketMQTemplate rocketMQTemplate;
    // 发送字符串消息到不同Topic
    public void sendToTopicA(String message) {
        rocketMQTemplate.convertAndSend("TopicA", message);
    }

    // 发送带Tag的复杂对象消息
    public void sendToTopicB(ApplyDTO order) {
        rocketMQTemplate.send("TopicB:TagB",
                MessageBuilder.withPayload(order).setHeader(RocketMQHeaders.KEYS,order.getUserId())
                        .setHeader("businessKey", order.getUserId()).build());
    }

    // 发送事务消息到第三个Topic
    public void sendTransactionalMsg(String msg) {
        rocketMQTemplate.sendMessageInTransaction("TransactionGroup-TopicC", MessageBuilder.withPayload(msg).build(), null);
    }
}
