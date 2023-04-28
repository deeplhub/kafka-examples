package com.xh.kafka.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * @author H.Yang
 * @date 2023/4/28
 */
@Slf4j
@RestController
public class DemoController {

    @Resource
    private ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;

    @GetMapping("/send1")
    public String sendMessage1() {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("test_topic", "测试消息....");
        RequestReplyFuture<String, String, String> replyFuture = replyingKafkaTemplate.sendAndReceive(producerRecord);
        ConsumerRecord<String, String> consumerRecord;
        try {
            consumerRecord = replyFuture.get();
            log.info("Receive reply success, result: {}", consumerRecord.value());
        } catch (InterruptedException | ExecutionException e) {
            log.error("Receive reply failure", e);
        }

        return "success";
    }


}
