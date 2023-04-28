package com.xh.kafka.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.protocol.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 批量消费 - 手动确认
 *
 * @author H.Yang
 * @date 2023/4/27
 */

@Slf4j
@Component
public class ConsumerExample {


    @KafkaListener(topics = "test1_topic", containerFactory = "manualListenerContainerFactory")
    public void biz1Consumer(List<String> messages, Acknowledgment ack) {
        log.info("[biz1Consumer] RECV MSG COUNT: {}", messages.size());
        log.info("[biz1Consumer] RECV MSG[0]: {}", messages.get(0));
        //确认单当前消息（及之前的消息）offset均已被消费完成
        ack.acknowledge();

        //拒绝消息列表中指定index（发生错误的消息index）对应的消息（此方法仅适用于listener.type=batch），
        //当前指定index之前的消息会被成功提交，
        //当前poll查询出的剩余消息记录（包括当前指定的index）均被抛弃，
        //且当前消费线程在阻塞指定sleep（如下3000毫秒）后重新调用poll获取待消费消息（包括当前index及之前poll抛弃的消息）
        //如下即确认当前list中前5条消息（0-4），抛弃当前list中后续消息，3秒后再次poll查询未消费消息
        //ack.nack(5, 3000);
    }


    @KafkaListener(topics = "test2_topic", containerFactory = "manualListenerContainerFactory")
    public void biz2Consumer(List<Message> messages, Acknowledgment ack) {
        log.info("[biz2Consumer] RECV MSG COUNT: {}", messages.size());
        log.info("[biz2Consumer] RECV MSG[0]: {}", messages.get(0));
        //确认单当前消息（及之前的消息）offset均已被消费完成
        ack.acknowledge();
    }


    /**
     * topics 中 是读取配置 该写法可以监听多个topics
     * 如果不设置 manualListenerContainerFactory 下面的ack 会获取不到
     * 也可以改成配置的方式 则不需要写Bean 了 两种结果都是一样的
     *
     * @param records
     * @param ack
     */
    @KafkaListener(topics = "test3_topic", containerFactory = "manualListenerContainerFactory")
    public void listen(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        try {
            //解析消息
            log.info("kafka成功，当前成功的批次。data:{}", records);
            for (ConsumerRecord record : records) {
                //处理消息
                log.info("处理消息 : {}", record);
            }
            //手动提交 至关重要
            ack.acknowledge();
        } catch (Exception e) {
            log.error("kafka失败，当前失败的批次。data:{}", records);
            e.printStackTrace();
            ack.nack(500);
        }
    }

}
