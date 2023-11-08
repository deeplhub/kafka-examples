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

    @GetMapping("/send")
    public String sendMessage() throws Exception {
        IntStream.range(1, 100).forEach(i -> {
            kafkaTemplate.send("filter_topic", String.valueOf(i));
        });
        return "success";
    }


}
