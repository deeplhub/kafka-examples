package com.xh.kafka.config;

import com.xh.kafka.templete.KafkaEnhanceTemplate;
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
 * MQ 增强自动配置
 *
 * @author H.Yang
 * @date 2023/4/24
 */
@Slf4j
@Configuration
public class KafkaEnhanceAutoConfiguration {

    /**
     * 注入增强的KafkaEnhanceTemplate
     */
    @Bean
    public KafkaEnhanceTemplate kafkaEnhanceTemplate(KafkaTemplate kafkaTemplate) {
        return new KafkaEnhanceTemplate(kafkaTemplate);
    }

    // FIXME 增强的KafkaEnhanceTemplate下不生效
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
}
