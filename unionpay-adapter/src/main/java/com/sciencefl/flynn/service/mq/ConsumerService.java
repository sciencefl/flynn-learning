package com.sciencefl.flynn.service.mq;

import com.sciencefl.flynn.dao.entity.UnionPayApply;
import com.sciencefl.flynn.dto.ApplyDTO;
import com.sciencefl.flynn.service.UnionPayApplyService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RocketMQMessageListener(topic ="TopicB",consumerGroup = "union-pay-consumer-group",selectorExpression = "TagB")
public class ConsumerService implements RocketMQListener<ApplyDTO> , RocketMQPushConsumerLifecycleListener {

    @Autowired
    UnionPayApplyService unionPayApplyService;
    @Override
    public void onMessage(ApplyDTO applyDTO) {
        UnionPayApply unionPayApply = new UnionPayApply();
        unionPayApply.setUserId(applyDTO.getUserId());
        unionPayApply.setCouponId(applyDTO.getCouponId());
        unionPayApply.setAgeRange(applyDTO.getAgeRange());
        unionPayApply.setBankAccount(applyDTO.getBankAccount());
        unionPayApply.setAmount(applyDTO.getAmount());
        unionPayApply.setTest(applyDTO.getTest());
        unionPayApplyService.save(unionPayApply);
    }
    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName("order-instance-" + UUID.randomUUID());
    }
}
