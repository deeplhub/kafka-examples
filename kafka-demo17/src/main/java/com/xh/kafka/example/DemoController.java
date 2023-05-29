package com.xh.kafka.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Type;

/**
 * @author H.Yang
 * @date 2023/4/28
 */
@Slf4j
@RestController
public class DemoController {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/send1")
    public String sendMessageWithDelay() throws Exception {

        // 发送消息，并设置消息的 TTL
        // "delayed_topic", "测试延迟消息..."

        return "success";
    }


}
