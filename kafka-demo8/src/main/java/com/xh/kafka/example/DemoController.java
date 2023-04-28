package com.xh.kafka.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author H.Yang
 * @date 2023/4/28
 */
@Slf4j
@RestController
public class DemoController {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/send1")
    public String sendMessage1() {
        // 这里Topic如果不存在，会自动创建
        kafkaTemplate.send("test1_topic", "测试消息1...");

        return "success";
    }

    @GetMapping("/send2")
    public String sendMessage2() {
        // 这里Topic如果不存在，会自动创建
        kafkaTemplate.send("test2_topic", "测试消息2...");

        return "success";
    }

    @GetMapping("/send3")
    public String sendMessage3() {
        // 这里Topic如果不存在，会自动创建
        kafkaTemplate.send("test3_topic", "测试消息3...");

        return "success";
    }

}
