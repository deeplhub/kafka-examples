package com.xh.kafka.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

/**
 * Spring-Kafka 通过实现自定义的 SeekToCurrentErrorHandler ，当 Consumer 消费消息异常的时候，进行拦截处理：
 * 重试小于最大次数时，重新投递该消息给 Consumer
 * 重试到达最大次数时，如果Consumer 还是消费失败时，该消息就会发送到死信队列。 死信队列的 命名规则为： 原有 Topic + .DLT 后缀 = 其死信队列的 Topic
 *
 * @author H.Yang
 * @date 2023/4/28
 */
@Slf4j
@Configuration
public class KafkaConfig {

    // 两个bean都可以

    @Bean
    public ErrorHandler kafkaErrorHandler(KafkaTemplate<?, ?> template) {
        log.info("kafkaErrorHandler begin to Handle");
        // <1> 创建 DeadLetterPublishingRecoverer 对象
        ConsumerRecordRecoverer recoverer = new DeadLetterPublishingRecoverer(template);
        // <2> 创建 FixedBackOff 对象   设置重试间隔 5秒 次数为 3次
        BackOff backOff = new FixedBackOff(5 * 1000L, 3L);
        // <3> 创建 SeekToCurrentErrorHandler 对象
        return new SeekToCurrentErrorHandler(recoverer, backOff);
    }

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer configurer, ConsumerFactory<Object, Object> kafkaConsumerFactory, KafkaTemplate<Object, Object> template) {
//        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        configurer.configure(factory, kafkaConsumerFactory);
//        //最大重试三次
//        BackOff backOff = new FixedBackOff(5 * 1000L, 3L);
//        factory.setErrorHandler(new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(template), backOff));
//        return factory;
//    }


}
