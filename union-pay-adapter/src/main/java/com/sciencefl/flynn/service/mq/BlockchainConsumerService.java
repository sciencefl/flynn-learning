package com.sciencefl.flynn.service.mq;

import com.sciencefl.flynn.service.BlockchainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@RocketMQMessageListener(
        topic = "${rocketmq.consumer.blockchain.topic}",
        consumerGroup = "${rocketmq.consumer.blockchain.group}",
        consumeMode = ConsumeMode.ORDERLY,    // 设置顺序消费模式
        maxReconsumeTimes = 3
)
public class BlockchainConsumerService implements RocketMQListener<MessageExt>,
        RocketMQPushConsumerLifecycleListener {

    private final RedisTemplate<String, String> redisTemplate;
    // 注入区块链服务
    private final BlockchainService blockchainService;

    @Override
    public void onMessage(MessageExt messageExt) {
         log.info("blockchain consumer 开始消费：{},id:{}",messageExt.getQueueOffset(),messageExt.getMsgId());
//        String messageId = message.getMessageId();
//        String businessId = message.getBusinessId();
//        String processKey = String.format("mq:blockchain:consumed:processing:%s", messageId);
//        String successKey = String.format("mq:blockchain:consumed:success:%s", messageId);
//
//        MDC.put("traceId", message.getTraceId());
//        try {
//            // 幂等性检查
//            String consumeKey = String.format("mq:blockchain:consumed:%s", messageId);
//            if (!redisTemplate.opsForValue().setIfAbsent(consumeKey, "1", 24, TimeUnit.HOURS)) {
//                log.info("消息已处理,跳过: messageId={}, businessId={}", messageId, businessId);
//                return;
//            }
//
//            // 调用区块链服务处理
//            ApplyDTO data = message.getData();
//            blockchainService.processMessage(data, message.getMessageType());
//
//            log.info("区块链消费成功: messageId={}, businessId={}", messageId, businessId);
//
//        } catch (Exception e) {
//            log.error("区块链消费失败: messageId={}, businessId={}", messageId, businessId, e);
//            // 抛出异常以触发重试
//            throw new RuntimeException("区块链消费失败", e);
//        } finally {
//            MDC.remove("traceId");
//        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName("blockchain-consumer-" + UUID.randomUUID());
        consumer.setMaxReconsumeTimes(3); // 最大重试次数
        // 顺序消费相关配置
        consumer.setSuspendCurrentQueueTimeMillis(1000);   // 消费失败时暂停队列的时间
        consumer.setConsumeTimeout(15000); // 消费超时时间
    }
}
