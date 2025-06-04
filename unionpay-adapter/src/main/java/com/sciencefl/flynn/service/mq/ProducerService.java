package com.sciencefl.flynn.service.mq;

import com.sciencefl.flynn.common.ResultCode;
import com.sciencefl.flynn.common.message.BaseMessage;
import com.sciencefl.flynn.common.message.UnionPayMessageWrapper;
import com.sciencefl.flynn.dto.ApplyDTO;
import com.sciencefl.flynn.exception.BusinessException;
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

    // 发送带Tag的复杂对象消息
    public SendResult sendMessage(ApplyDTO applyDTO,String messageType) {
        // 构建消息
        BaseMessage<ApplyDTO> message = UnionPayMessageWrapper.build(applyDTO, messageType);
        message.setBusinessId(applyDTO.getUserId() + "_" + applyDTO.getCouponId());

        // 使用用户ID+券ID作为分区键,保证同一用户同一券的顺序
        String hashKey = applyDTO.getUserId() + "_" + applyDTO.getCouponId();
        try {
            // 通message进行tag分类
            SendResult sendResult = rocketMQTemplate.syncSendOrderly(
                    "union-pay-topic:" + messageType,
                    MessageBuilder.withPayload(message)
                            .setHeader(RocketMQHeaders.KEYS, message.getMessageId())
                            .setHeader("businessId", message.getBusinessId())
                            .build(),
                    hashKey,
                    3000
            );

            log.info("消息发送成功: messageId={}, messageType={}, result={}",
                    message.getMessageId(), messageType, sendResult);

            return sendResult;
        } catch (Exception e) {
            log.error("消息发送失败: messageId={}, messageType={}",
                    message.getMessageId(), messageType, e);
            throw new BusinessException(ResultCode.BUSINESS_ERROR,e.getMessage());
        }
    }
}
