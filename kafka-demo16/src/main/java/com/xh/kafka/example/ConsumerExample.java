package com.xh.kafka.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.FixedDelayStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

/**
 * @author H.Yang
 * @date 2023/4/27
 */
@Slf4j
@Component
public class ConsumerExample {

    @RetryableTopic(attempts = "4", backoff = @Backoff(delay = 3000), fixedDelayTopicStrategy = FixedDelayStrategy.SINGLE_TOPIC)
    @KafkaListener(topics = "test_topic")
    public void onMessage1(String message) {
        log.info("Receive message: {}", message);
        throw new RuntimeException("Something wrong!");
    }


    @DltHandler
    public void handleDlt(String message) {
        log.info("死信队列消费端：{}", message);
    }
}
