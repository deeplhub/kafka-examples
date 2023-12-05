package com.xh.kafka.test;

import com.xh.kafka.model.BaseMessageModel;
import com.xh.kafka.templete.KafkaEnhanceTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * @author H.Yang
 * @date 2023/4/28
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerExampleTest {

    @Resource
    private KafkaEnhanceTemplate<Object, Object> kafkaEnhanceTemplate;

    @Test
    public void orderMessage() throws InterruptedException {
        BaseMessageModel messageModel = new BaseMessageModel();

        messageModel.setKey(System.currentTimeMillis() + "");
        messageModel.setSource("ORDER");
        messageModel.setBody("发送一条订单消息到消息队列！");

        kafkaEnhanceTemplate.sendSync("order_topic", messageModel);

        //kafkaEnhanceTemplate.sendSync("order_topic", "发送一条订单消息到消息队列！");

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
