package com.xh.kafka.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

/**
 * @author H.Yang
 * @date 2023/4/27
 */
@Slf4j
@Component
public class ConsumerExample {
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 3000))
    @KafkaListener(topics = "test_topic")
    public void onMessage1(String message) {
        log.info("Receive message: {}", message);
        throw new RuntimeException("Something wrong!");
    }


    /**
     * 死信
     *
     * @param message
     */
    @KafkaListener(topics = "test_topic.DLT")
    public void messListenerDLT(String message) {
        System.out.println("死信队列消费端 收到消息：" + message);
    }


}
