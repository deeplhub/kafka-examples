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
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

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

    /**
     * 第一条消费发送后，在发第二条消息前出现了异常，那么第一条已经发送的消息会回滚。而且正常情况下，在消息一发送后休眠一段时间，在发送第二条消息，消费端也只有在事务方法执行完成后才会接收到消息
     */
    @Test
    @SneakyThrows
    public void one() {
        // 声明事务：如果报错消息不会发出去
        kafkaTemplate.executeInTransaction(operations -> {
            operations.send("transaction_topic", "事务消息...");

            if (true) {
                throw new RuntimeException("failed");
            }
            return true;
        });

        // 不声明事务：后面报错但前面消息已经发送成功了
        kafkaTemplate.send("transaction_topic", "事务消息...");

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }


}
