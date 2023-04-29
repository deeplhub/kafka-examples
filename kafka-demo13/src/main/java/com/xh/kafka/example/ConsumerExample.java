package com.xh.kafka.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author H.Yang
 * @date 2023/4/27
 */
@Slf4j
@Component
public class ConsumerExample {

    @KafkaListener(topics = "test_topic", errorHandler = "customKafkaListenerErrorHandler")
    public void onMessage1(String message) {
        log.info("Receive message: {}", message);

        throw new RuntimeException("error");
    }


}
