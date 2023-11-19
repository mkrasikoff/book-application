package com.mkrasikoff.userservice.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class UserCreatedProducer {

    private final String userCreatedTopic = "user-created-topic";
    private static final Logger log = LoggerFactory.getLogger(UserCreatedProducer.class);

    private final KafkaTemplate<String, Long> kafkaTemplate;

    @Autowired
    public UserCreatedProducer(KafkaTemplate<String, Long> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserId(Long userId) {
        ListenableFuture<SendResult<String, Long>> future = kafkaTemplate.send(userCreatedTopic, userId);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, Long> result) {
                log.info("Successfully sent UserId to Kafka topic. UserId=[{}], Offset=[{}]", userId, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable exception) {
                log.error("Failed to send UserId to Kafka topic. UserId=[{}], Error=[{}]", userId, exception.getMessage());
            }
        });
    }
}
