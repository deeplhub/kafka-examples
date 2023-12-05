package com.xh.kafka.example;

import com.xh.kafka.handler.EnhanceMessageHandler;
import com.xh.kafka.model.BaseMessageModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author H.Yang
 * @date 2023/4/20
 */
@Slf4j
@Component
public class OrderConsumer extends EnhanceMessageHandler<String, BaseMessageModel> {

    @KafkaListener(topics = "order_topic")
    public void onMessage(ConsumerRecord<String, BaseMessageModel> record) {
        // 这里不处理业务，而是先委派给父类做基础操作，然后父类做完基础操作后会调用子类的实际处理
        super.dispatchMessage(record);
    }

    @Override
    protected void handleMessage(BaseMessageModel message) throws Exception {
        //log.info("消费者消息来源[{}]，收到消息内容[{}]", message.getSource(), message.getBody());
        throw new RuntimeException("故意抛个异常");
    }

    @Override
    protected void handleMaxRetriesExceeded(BaseMessageModel message) {

    }

    @Override
    protected boolean isRetry() {
        return true;
    }

    @Override
    protected boolean throwException() {
        return false;
    }

    @Override
    protected int maxRetryTimes() {
        // 指定需要的重试次数，超过重试次数overMaxRetryTimesMessage会被调用
        return 2;
    }
}
