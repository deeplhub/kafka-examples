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
    public void sendMsg() {
        // 这里Topic如果不存在，会自动创建
        kafkaTemplate.send("test_topic", "测试消息...");
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
