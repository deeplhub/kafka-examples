package com.xh.kafka.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author H.Yang
 * @date 2023/4/29
 */
@Slf4j
@Component
public class CustomKafkaListenerErrorHandler implements KafkaListenerErrorHandler {
    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
        log.error("Handle message with exception, message: {}", message.getPayload().toString());
        return null;
    }

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
        log.error("Handle message with exception, message: {}", message.getPayload().toString());
        return null;
    }
}
