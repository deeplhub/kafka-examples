package com.xh.kafka.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.stream.IntStream;

/**
 * @author H.Yang
 * @date 2023/4/28
 */
@Slf4j
@RestController
public class DemoController {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/sendBath1")
    public String sendBath1() {
        IntStream.range(1, 100000).forEach(i -> {
            kafkaTemplate.send("bath_topic", "hello world: " + i);
        });

        return "success";
    }

    @GetMapping("/sendBath2")
    public String sendBath2() {
        IntStream.range(1, 100000).forEach(i -> {
            kafkaTemplate.send("bath_topic", "hello world: " + i);
        });

        return "success";
    }

    @GetMapping("/sendBath3")
    public String sendBath3() {
        IntStream.range(1, 100000).forEach(i -> {
            kafkaTemplate.send("bath_topic", "hello world: " + i);
        });

        return "success";
    }

}
