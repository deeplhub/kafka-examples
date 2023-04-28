package com.xh.kafka.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 单记录消费 - 手动确认
 *
 * @author H.Yang
 * @date 2023/4/27
 */
@Slf4j
@Component
public class ConsumerExample {

    /**
     * 手动ack  提交记录
     *
     * @param record
     * @param ack
     * @return
     */
    @KafkaListener(topics = "test1_topic", containerFactory = "kafkaManualAckListenerContainerFactory")
    public String onMessage1(ConsumerRecord<?, ?> record, Acknowledgment ack) {
        log.info(record.toString());

        //确认单当前消息（及之前的消息）offset均已被消费完成
        ack.acknowledge();
        //拒绝当前消息（此方法仅适用于listener.type=single）
        //当前poll查询出的剩余消息记录均被抛弃，
        //且当前消费线程在阻塞指定sleep（如下3000毫秒）后重新调用poll获取待消费消息（包括之前poll被抛弃的消息）
        //ack.nack(3000)

        return "successful";
    }

    @KafkaListener(topics = "test2_topic", containerFactory = "kafkaManualAckListenerContainerFactory")
    public void onMessage2(ConsumerRecord<?, ?> record, Acknowledgment ack) {
        log.info(record.toString());

        //确认单当前消息（及之前的消息）offset均已被消费完成
        ack.acknowledge();
        //拒绝当前消息（此方法仅适用于listener.type=single）
        //当前poll查询出的剩余消息记录均被抛弃，
        //且当前消费线程在阻塞指定sleep（如下3000毫秒）后重新调用poll获取待消费消息（包括之前poll被抛弃的消息）
        //ack.nack(3000)
    }

}
