package com.example.ums.service.kafka;

import com.example.ums.model.UserRequest;
import com.example.ums.service.UserService;
import com.example.ums.util.TransformUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty("kafka.enabled")
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Autowired
    private UserService userService;

    @KafkaListener(topics = "${kafka.user.topic}", groupId = "${kafka.consumer.groupId}", containerFactory = "kafkaManualAckListenerContainerFactory")
    public void receiveMessage(@Payload String kafkaMessage) {
        parseConvertAndProcess(kafkaMessage);
    }

    private void parseConvertAndProcess(String kafkaMessage) {
        try {
            UserRequest request = TransformUtil.fromJson(kafkaMessage, UserRequest.class);
            userService.createUser(request);
        } catch (Exception e) {
            log.warn("KafkaUserConsumer: {} with error message {}", kafkaMessage, e.getMessage());
        }
    }

}
