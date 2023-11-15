package com.mkrasikoff.bookservice.producer;

import com.mkrasikoff.userservice.producer.UserCreatedProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class UserCreatedProducerTest {

    @InjectMocks
    private UserCreatedProducer userCreatedProducer;

    @Mock
    private KafkaTemplate<String, Long> kafkaTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendUserId_whenCalled_thenSendsMessageToKafka() {
        Long userId = 1L;
        String userCreatedTopic = "user-created-topic";

        userCreatedProducer.sendUserId(userId);

        verify(kafkaTemplate).send(eq(userCreatedTopic), eq(userId));
    }
}
