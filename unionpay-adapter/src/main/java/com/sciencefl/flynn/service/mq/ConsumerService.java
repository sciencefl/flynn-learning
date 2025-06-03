package com.sciencefl.flynn.service.mq;

import com.sciencefl.flynn.common.message.BaseMessage;
import com.sciencefl.flynn.dao.UnionPayApplyDao;
import com.sciencefl.flynn.dao.entity.UnionPayApply;
import com.sciencefl.flynn.dto.ApplyDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RocketMQMessageListener(
        topic ="TopicB",
        consumerGroup = "union-pay-consumer-group",
        selectorExpression = "TagB")
public class ConsumerService implements RocketMQListener<BaseMessage<ApplyDTO>> , RocketMQPushConsumerLifecycleListener {

    @Autowired
    UnionPayApplyDao unionPayApplyDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void onMessage(BaseMessage<ApplyDTO> message) {
        try {
            // 设置追踪ID
            MDC.put("traceId", message.getTraceId());

            // 幂等性检查
            String messageKey = "mq:consumed:" + message.getMessageId();
            if (Boolean.FALSE.equals(redisTemplate.opsForValue().setIfAbsent(messageKey, "1", 24, TimeUnit.HOURS))) {
                log.info("消息已处理，跳过: {}", message.getMessageId());
                return;
            }

            // 获取业务数据
            ApplyDTO applyDTO = message.getData();

            // 业务处理
            UnionPayApply unionPayApply = new UnionPayApply();
            unionPayApply.setUserId(applyDTO.getUserId());
            unionPayApply.setCouponId(applyDTO.getCouponId());
            unionPayApplyDao.save(unionPayApply);

            log.info("ConsumerService消息处理成功: {}", message.getMessageId());
        } catch (Exception e) {
            log.error("ConsumerService消息处理失败: {}", message.getMessageId(), e);
            throw new RuntimeException("ConsumerService消息处理失败", e);
        } finally {
            MDC.remove("traceId");
        }
//        UnionPayApply unionPayApply = new UnionPayApply();
//        unionPayApply.setUserId(applyDTO.getUserId());
//        unionPayApply.setCouponId(applyDTO.getCouponId());
//        unionPayApply.setAgeRange(applyDTO.getAgeRange());
//        unionPayApply.setBankAccount(applyDTO.getBankAccount());
//        unionPayApply.setAmount(applyDTO.getAmount());
//        unionPayApply.setTest(applyDTO.getTest());
//        unionPayApplyDao.save(unionPayApply);
//        log.info("ConsumerService1 接收到消息: {}", applyDTO.getUserId());
    }
    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName("order-instance-" + UUID.randomUUID());
    }
}
