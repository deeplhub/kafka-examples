package com.xh.kafka.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author H.Yang
 * @date 2023/4/27
 */
@Slf4j
@EnableScheduling
@Component
public class ConsumerExample {

    /**
     * @KafkaListener注解所标注的方法并不会在IOC容器中被注册为Bean， 而是会被注册在KafkaListenerEndpointRegistry中，
     * 而KafkaListenerEndpointRegistry在SpringIOC中已经被注册为Bean
     **/
    @Resource
    private KafkaListenerEndpointRegistry registry;


    @KafkaListener(id = "cron_timer", topics = "scheduled_topic", containerFactory = "delayContainerFactory")
    public void onMessage1(String message) {

        log.info("Received message: {}", message);
    }


    /**
     * 定时启动监听器
     */
    @Scheduled(cron = "0 * * * * ? ")
    public void startListener() {
        System.out.println("启动监听器...");
        // 判断监听容器是否启动，未启动则将其启动。"cron_timer"是@KafkaListener注解后面设置的监听器ID,标识这个监听器
        if (!registry.getListenerContainer("cron_timer").isRunning()) {
            registry.getListenerContainer("cron_timer").start();
        }

        // 项目启动的时候监听容器是未启动状态，而resume是恢复的意思不是启动的意思
        registry.getListenerContainer("cron_timer").resume();
    }

    /**
     * 定时停止监听器
     */
    @Scheduled(cron = "30 * * * * ? ")
    public void shutDownListener() {
        System.out.println("关闭监听器...");
        registry.getListenerContainer("cron_timer").pause();
    }
}
