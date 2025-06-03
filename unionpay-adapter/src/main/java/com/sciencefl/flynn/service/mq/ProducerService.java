package com.sciencefl.flynn.service.mq;

import com.sciencefl.flynn.common.message.BaseMessage;
import com.sciencefl.flynn.common.message.UnionPayMessageWrapper;
import com.sciencefl.flynn.dto.ApplyDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProducerService {

    @Autowired
    RocketMQTemplate rocketMQTemplate;
    // 发送字符串消息到不同Topic
    public void sendToTopicA(String message) {
        rocketMQTemplate.convertAndSend("TopicA", message);
    }

    // 发送带Tag的复杂对象消息
    public void sendToTopicB(ApplyDTO applyDTO) {
        BaseMessage<ApplyDTO> wrappedMessage = UnionPayMessageWrapper.build(applyDTO, "APPLY");
        wrappedMessage.setBusinessId(applyDTO.getUserId());
        SendResult sendResult = rocketMQTemplate.syncSendOrderly("TopicC:TagC",
                MessageBuilder.withPayload(wrappedMessage)
                        .setHeader(RocketMQHeaders.KEYS, wrappedMessage.getMessageId())
                        .setHeader("businessId", wrappedMessage.getBusinessId())
                        .build(),applyDTO.getUserId(),3000);

        log.info("消息发送结果: {}", sendResult);
//        rocketMQTemplate.send("TopicB:TagB",
//                MessageBuilder.withPayload(wrappedMessage).setHeader(RocketMQHeaders.KEYS,wrappedMessage.getMessageId())
//                        .setHeader("businessKey", applyDTO.getUserId()).build());
//
//        // 通过消费券的id作为业务Key发送
//        // rocketMQTemplate.syncSendOrderly("order_topic", "订单创建", "ORDER_1001");
    }

    // 发送事务消息到第三个Topic
    public void sendTransactionalMsg(String msg) {
        rocketMQTemplate.sendMessageInTransaction("TransactionGroup-TopicC", MessageBuilder.withPayload(msg).build(), null);
    }
}
