package com.xh.kafka.example.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * @author H.Yang
 * @date 2023/4/27
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductExampleTest {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    @SneakyThrows
    public void one() {
        // 这里Topic如果不存在，会自动创建
        kafkaTemplate.send("one_topic", "测试消息...").addCallback(successCallback -> {
            // 消息发送到的topic
            String topic = successCallback.getRecordMetadata().topic();
            // 消息发送到的分区
            int partition = successCallback.getRecordMetadata().partition();
            // 消息在分区内的offset
            long offset = successCallback.getRecordMetadata().offset();
            log.info("发送消息成功: {} - {} - {}", topic, partition, offset);
        }, failureCallback -> {
            log.info("发送消息失败:{}", failureCallback.getMessage());
        });
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    @SneakyThrows
    public void two() {
        // 这里Topic如果不存在，会自动创建
        kafkaTemplate.send("two_topic", "测试消息...").addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("发送消息失败", throwable);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("发送消息成功，{} - {} -{}", result.getRecordMetadata().topic(), result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
            }
        });
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
