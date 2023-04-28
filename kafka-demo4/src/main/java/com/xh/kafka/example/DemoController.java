package com.xh.kafka.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     * 第一条消费发送后，在发第二条消息前出现了异常，那么第一条已经发送的消息会回滚。而且正常情况下，在消息一发送后休眠一段时间，在发送第二条消息，消费端也只有在事务方法执行完成后才会接收到消息
     *
     * @return
     */
    @GetMapping("/send1")
    public String sendMessage1() {
        // 声明事务：如果报错消息不会发出去
        kafkaTemplate.executeInTransaction(operations -> {
            operations.send("transaction_topic", "事务消息...");

            if (true) {
                throw new RuntimeException("failed");
            }
            return true;
        });

        // 不声明事务：后面报错但前面消息已经发送成功了
        kafkaTemplate.send("transaction_topic", "事务消息...");

        return "success";
    }


    /**
     *
     * @param str
     * @return
     */
    @GetMapping("/send2")
    @Transactional(rollbackFor = Exception.class)
    public String sendMessage2(String str) {
        kafkaTemplate.send("transaction_topic", "事务消息...");
        log.info("Send in transaction by annotation success");
        if ("err".equals(str)) {
            throw new RuntimeException("failed");
        }
        return "success";
    }


}
