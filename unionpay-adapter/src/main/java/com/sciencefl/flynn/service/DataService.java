package com.sciencefl.flynn.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataService {
//    private final KafkaTemplate<String, Object> kafkaTemplate;
//    private final UnionpayConfig unionpayConfig;
//
//    private static final Map<String, String> TOPIC_MAPPING = new HashMap<>();
//
//    @PostConstruct
//    public void initTopicMapping() {
//        TOPIC_MAPPING.put("CREATE_BATCH", unionpayConfig.getTopics().getApply());
//        TOPIC_MAPPING.put("CONSUME", unionpayConfig.getTopics().getUse());
//        TOPIC_MAPPING.put("REFUND", unionpayConfig.getTopics().getRefund());
//        TOPIC_MAPPING.put("RETURN", unionpayConfig.getTopics().getReturn());
//    }
//
//    @CheckRequestId
//    public void processRequest(BatchRequest request) {
//        String topic = TOPIC_MAPPING.get(request.getOperation());
//        if (topic == null) {
//            throw new BusinessException("非法的操作类型: " + request.getOperation());
//        }
//
//        request.getData().forEach(item -> {
//            kafkaTemplate.send(topic, UUID.randomUUID().toString(), item);
//            log.info("已发送消息到Kafka主题: {}, 数据: {}", topic, item);
//        });
//    }
}
