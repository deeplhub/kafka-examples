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

//    @KafkaListener(topics = "transaction_topic")
//    public void onMessage(String message) {
//        log.info("Received message: {}", message);
//    }


    @KafkaListener(topics = "transaction_topic")
    public void onMessage(ConsumerRecord<?, ?> record) {
        // 消费的哪个topic、partition的消息,打印出消息内容
        log.info("partition: {}", record.partition());
        log.info("offset: {}", record.offset());
        log.info("key: {}", record.key());
        log.info("value: {}", record.value());

    }
}
