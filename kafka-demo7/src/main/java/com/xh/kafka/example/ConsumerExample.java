package com.xh.kafka.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 批量消费 - 自动确认
 *
 * @author H.Yang
 * @date 2023/4/27
 */
@Slf4j
@Component
public class ConsumerExample {

    @KafkaListener(topics = "bath_topic")
    public void onMessage(List<String> list) {
        log.info("Number of received message lines: {}", list.size());
    }

}
