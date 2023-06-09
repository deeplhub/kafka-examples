package com.xh.kafka.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author H.Yang
 * @date 2023/4/27
 */
@Slf4j
@Component
public class ConsumerExample {

    @KafkaListener(topics = "one_topic")
    public void onMessage(String message) {
        log.info("Received message: {}", message);
    }

    @KafkaListener(topics = "two_topic")
    public void onMessage(ConsumerRecord<String, String> record) {
        // 消费的哪个topic、partition的消息,打印出消息内容
        log.info("partition: {}", record.partition());
        log.info("offset: {}", record.offset());
        log.info("key: {}", record.key());
        log.info("value: {}", record.value());
    }

    @KafkaListener(topics = {"topic1", "topic2", "topic3"})
    public void onMessage1(ConsumerRecord<String, String> record) {
        // 监听多个消息，但只能监听一个消息的类型，不支持任何消息类型
        log.info("partition: {}", record.partition());
        log.info("topic: {}", record.topic());
        log.info("offset: {}", record.offset());
        log.info("key: {}", record.key());
        log.info("value: {}", record.value());
    }
}
