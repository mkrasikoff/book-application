package com.mkrasikoff.userservice.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserCreatedProducer {

    @Value("${kafka.topic.userCreated}")
    private String userCreatedTopic;

    private final KafkaTemplate<String, Long> kafkaTemplate;

    @Autowired
    public UserCreatedProducer(KafkaTemplate<String, Long> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserId(Long userId) {
        kafkaTemplate.send(userCreatedTopic, userId);
    }
}
