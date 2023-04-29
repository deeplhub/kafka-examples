package com.xh.kafka.example;

import cn.hutool.json.JSONObject;
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
    private KafkaTemplate<Object, Object> kafkaTemplate;

    @GetMapping("/send1")
    public String sendMessage1() {
        // 这里Topic如果不存在，会自动创建
        kafkaTemplate.send("test_topic", "测试消息....");

        return "success";
    }

    @GetMapping("/send2")
    public String sendMessage2() {
        // 使用此方法发送消息时需要把消费者序列化改为JSON形式，否则会报错
        MessageDTO dto = new MessageDTO();
        dto.setKey("test2_topic");
        dto.setBody("测试消息....");

        kafkaTemplate.send("test_topic", dto);

        return "success";
    }

    @GetMapping("/send3")
    public String sendMessage3() {
        // 使用此方法发送消息时需要把消费者序列化改为JSON形式，否则会报错
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", "测试消息");

        kafkaTemplate.send("test_topic", jsonObject);

        return "success";
    }


}
