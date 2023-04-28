package com.xh.kafka.example.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

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
        kafkaTemplate.send("one_topic", "测试消息...");
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    @SneakyThrows
    public void two() {
        // 这里Topic如果不存在，会自动创建
        kafkaTemplate.send("two_topic", "测试消息...");
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    @SneakyThrows
    public void three() {
        // 这里Topic如果不存在，会自动创建
        kafkaTemplate.send("topic1", "测试消息1...");
        kafkaTemplate.send("topic2", "测试消息2...");
        kafkaTemplate.send("topic3", "测试消息3...");
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
