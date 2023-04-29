package com.xh.kafka.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @author H.Yang
 * @date 2023/4/29
 */
@Slf4j
@Component
public class LoggingErrorHandler implements ErrorHandler {

    @Override
    public void handle(Exception thrownException, ConsumerRecord<?, ?> record) {
        log.error("Error while processing: {}", ObjectUtils.nullSafeToString(record), thrownException);
    }
}
