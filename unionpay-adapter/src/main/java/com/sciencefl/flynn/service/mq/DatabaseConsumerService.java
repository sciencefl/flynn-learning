package com.sciencefl.flynn.service.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sciencefl.flynn.common.message.BaseMessage;
import com.sciencefl.flynn.dto.ApplyDTO;
import com.sciencefl.flynn.service.processor.DatabaseMessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
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
        topic = "${rocketmq.consumer.database.topic}",
        consumerGroup = "${rocketmq.consumer.database.group}",
        messageModel = MessageModel.CLUSTERING,
        maxReconsumeTimes = 3,
        consumeMode = ConsumeMode.ORDERLY  // 设置顺序消费模式
)
public class DatabaseConsumerService implements RocketMQListener<MessageExt> , RocketMQPushConsumerLifecycleListener {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private DatabaseMessageProcessor messageProcessor;


    @Override
    public void onMessage(MessageExt messageExt) {
        // 从MessageExt中直接获取重试次数
        int reconsumeTimes = messageExt.getReconsumeTimes();

        // 获取消息内容并转换
        String body = new String(messageExt.getBody());
        BaseMessage<ApplyDTO> message = JSON.parseObject(body, new TypeReference<BaseMessage<ApplyDTO>>() {});
        String messageId = message.getMessageId();
        try {
            setupMDC(message, reconsumeTimes);
            // 检查是否处理成功
            if (isProcessedSuccessfully(messageId)) {
                log.info("数据库消息已处理成功,跳过: messageId={}", messageId);
                return;
            }

            // 处理消息
            processMessageWithRetry(message,reconsumeTimes);

        } finally {
            MDC.clear();  // 清理所有MDC上下文
        }
    }

    private void setupMDC(BaseMessage<ApplyDTO> message, int reconsumeTimes) {
        String traceId = String.format("%s-retry%d-%s",
                message.getTraceId(), reconsumeTimes,
                UUID.randomUUID().toString().substring(0, 8));
        MDC.put("traceId", traceId);
        MDC.put("messageId", message.getMessageId());
        MDC.put("businessId", message.getBusinessId());
        MDC.put("retryTimes", String.valueOf(reconsumeTimes));
    }

    private boolean isProcessedSuccessfully(String messageId) {
        String successKey = String.format("mq:db:consumed:success:%s", messageId);
        return redisTemplate.hasKey(successKey);
    }
    private void processMessageWithRetry(BaseMessage<ApplyDTO> message,int retryTimes) {
        String messageId = message.getMessageId();
        String processKey = String.format("mq:db:consumed:processing:%s", messageId);
        String successKey = String.format("mq:db:consumed:success:%s", messageId);

        try {
            // 记录重试次数
            if (Boolean.FALSE.equals(redisTemplate.opsForValue().setIfAbsent(processKey,
                    String.valueOf(retryTimes), 5, TimeUnit.MINUTES))) {
                log.warn("消息正在处理中,等待重试,messageId={}, retryTimes={}",messageId, retryTimes);
                throw new RuntimeException("数据库消息正在处理中");
            }

            // 处理消息
            messageProcessor.processMessage(message.getData());

            // 标记成功
            redisTemplate.opsForValue().set(successKey, "1", 12, TimeUnit.HOURS);
            log.info("消息处理成功,messageID:{}, retryTimes={}",messageId,retryTimes);

        } catch (Exception e) {
            log.error("数据库消费失败: messageId={},retryTimes={},error:{}", message.getMessageId(),retryTimes, e.getMessage());;
            redisTemplate.delete(processKey);  // 释放处理锁
            throw new RuntimeException("消息处理失败", e);
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName("db-consumer-" + UUID.randomUUID());
        consumer.setMaxReconsumeTimes(3); // 最大重试次数
        // 顺序消费相关配置
        consumer.setSuspendCurrentQueueTimeMillis(1000); // 消费失败时暂停队列的时间
        consumer.setConsumeTimeout(15000); // 消费超时时间
    }
}
