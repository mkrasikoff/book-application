package com.mkrasikoff.bookservice.producer;

import com.mkrasikoff.userservice.producer.UserCreatedProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.SettableListenableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void sendUserId_whenCalled_thenSendsMessageToKafka() throws InterruptedException {
        Long userId = 1L;
        String userCreatedTopic = "user-created-topic";
        SettableListenableFuture<SendResult<String, Long>> future = new SettableListenableFuture<>();
        CountDownLatch latch = new CountDownLatch(1);
        when(kafkaTemplate.send(eq(userCreatedTopic), eq(userId))).thenReturn(future);

        userCreatedProducer.sendUserId(userId);

        verify(kafkaTemplate).send(eq(userCreatedTopic), eq(userId));
        // Simulate a successful send
        SendResult<String, Long> sendResult = new SendResult<>(null, null);
        future.set(sendResult);
        future.addCallback(result -> latch.countDown(), ex -> latch.countDown());
        boolean await = latch.await(1, TimeUnit.SECONDS);
        assert await;
    }
}
