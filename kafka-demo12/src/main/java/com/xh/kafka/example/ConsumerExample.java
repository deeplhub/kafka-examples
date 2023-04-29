package com.xh.kafka.example;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author H.Yang
 * @date 2023/4/27
 */
@Slf4j
@Component
@KafkaListener(topics = "test_topic")
public class ConsumerExample {

    @KafkaHandler
    public void onMessage1(String message) {
        log.info("Receive message: {}", message);
    }

    @KafkaHandler
    public void onMessage2(MessageDTO record) {
        log.info("Receive message: {}", record);
    }

    @KafkaHandler
    public void onMessage3(JSONObject record) {
        log.info("Receive message: {}", record);
    }

    @KafkaHandler(isDefault = true)
    public void def(ConsumerRecord<?, ?> record) {
        log.info("Receive message: {}", record);
    }


}
