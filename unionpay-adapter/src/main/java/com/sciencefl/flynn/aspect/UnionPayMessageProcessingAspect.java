package com.sciencefl.flynn.aspect;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sciencefl.flynn.common.message.BaseMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class UnionPayMessageProcessingAspect {
    @Around("@within(org.apache.rocketmq.spring.annotation.RocketMQMessageListener)")
    public Object aroundMessageProcessing(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String messageId = extractMessageId(joinPoint);
        String className = joinPoint.getTarget().getClass().getSimpleName();

        try {
            // 记录开始处理
            log.info("开始处理消息: class={}, messageId={}", className, messageId);

            Object result = joinPoint.proceed();

            // 记录处理时间
            long processingTime = System.currentTimeMillis() - startTime;
            log.info("消息处理完成: class={}, messageId={}, time={}ms",
                    className, messageId, processingTime);
            return result;

        } catch (Exception e) {
            // 记录失败指标
            log.error("消息处理异常: class={}, messageId={}, error={}",
                    className, messageId, e.getMessage(), e);
            throw e;
        }
    }

    private String extractMessageId(ProceedingJoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0) {
                // 处理 MessageExt 类型
                if (args[0] instanceof MessageExt) {
                    MessageExt messageExt = (MessageExt) args[0];
                    // 尝试从 MessageExt 中获取消息ID
                    BaseMessage<?> message = JSON.parseObject(new String(messageExt.getBody()),
                            new TypeReference<BaseMessage<?>>() {});
                    return messageExt.getMsgId();
                }
                // 处理 BaseMessage 类型
                else if (args[0] instanceof BaseMessage) {
                    BaseMessage<?> message = (BaseMessage<?>) args[0];
                    return message.getMessageId();
                }
            }
            return "UNKNOWN-" + UUID.randomUUID().toString();
        } catch (Exception e) {
            log.warn("提取消息ID失败", e);
            return "UNKNOWN-" + UUID.randomUUID().toString();
        }
    }
}
