package com.example.ums.service.kafka;

import com.example.ums.model.UserRequest;
import com.example.ums.util.TransformUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty("kafka.enabled")
public class KafkaProducerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);

    @Value("${kafka.user.topic}")
    private String userTopic;

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    /**
     * @param topic is the name of the topic to produce data in
     * @param key   is used to ensure that messages with same key go in same
     *              partitions
     * @param data
     */
    public void sendMessageToKafka(String topic, String key, String data) {
        try {
            kafkaTemplate.send(topic, key, data);
        } catch (Exception e) {
            log.info("Sending message to kafka failed {}", data, e);
        }
    }

    public void sendCreateUserRequestToKafka(UserRequest request) {
        if (request == null) {
            log.info("request to be produced to kafka is null");
            return;
        }
        String kafkaModel = TransformUtil.toJson(request);
        sendMessageToKafka(userTopic, request.getUserName(), kafkaModel);
    }

}
