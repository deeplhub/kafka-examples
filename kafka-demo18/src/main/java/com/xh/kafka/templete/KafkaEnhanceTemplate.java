package com.xh.kafka.templete;

import cn.hutool.json.JSONUtil;
import com.xh.kafka.model.BaseMessageModel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * RocketMQ模板类
 * <p>
 * <p>
 * RocketMQTemplate发送消息的代码如果不封装，我们发送消息需要这样String destination = topic + ":" + tag;template.syncSend(destination, message);
 * <p>
 * 每个人发送消息都要自己处理这个冒号，直接传入topic和tag不香吗？
 * <p>
 * 按照抽离变化点中的变化点，只有消息是变化的，除此之外的其他规则交给封装类
 * <p>
 * RocketMQTemplate主要封装发送消息的日志、异常的处理、消息key设置、等等其他配置封装
 *
 * @author H.Yang
 * @date 2023/4/22
 */
@Slf4j
@AllArgsConstructor
public class KafkaEnhanceTemplate<K, V> {
    private KafkaTemplate<K, V> kafkaTemplate;

    /**
     * 获取模板，如果封装的方法不够提供原生的使用方式
     */
    public KafkaTemplate<K, V> getTemplate() {
        return kafkaTemplate;
    }

    /**
     * 发送同步消息
     *
     * @param topic
     * @param message
     * @return
     */
    @SneakyThrows
    public <T extends BaseMessageModel> SendResult<K, V> sendSync(String topic, V message) {
        ListenableFuture<SendResult<K, V>> listenableFuture = kafkaTemplate.send(topic, message);
        SendResult<K, V> sendResult = listenableFuture.get();

        log.info("[{}]同步消息[{}]发送结果[{}]", sendResult.getRecordMetadata().topic(), message, JSONUtil.toJsonStr(sendResult));
        return sendResult;

    }

}
