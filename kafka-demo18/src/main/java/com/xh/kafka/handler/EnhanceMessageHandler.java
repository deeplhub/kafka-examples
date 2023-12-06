package com.xh.kafka.handler;

import com.xh.kafka.model.BaseMessageModel;
import com.xh.kafka.templete.KafkaEnhanceTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 抽象消息监听器，封装了所有公共处理业务，如：基础日志记录、异常处理、消息重试、警告通知
 *
 * @author H.Yang
 * @date 2023/4/22
 */
@Slf4j
public abstract class EnhanceMessageHandler<K, V extends BaseMessageModel> {

    /**
     * 重试前缀
     */
    private static final String RETRY_PREFIX = "RETRY_";

    @Resource
    private KafkaEnhanceTemplate enhanceTemplate;

    /**
     * 消息处理
     *
     * @param message 待处理消息
     * @throws Exception 消费异常
     */

    protected abstract void handleMessage(V message) throws Exception;

    /**
     * 超过重试次数消息，需要启用isRetry
     *
     * @param message 待处理消息
     */

    protected abstract void handleMaxRetriesExceeded(V message);

    /**
     * 是否过滤消息，例如某些
     *
     * @param message 待处理消息
     * @return true: 本次消息被过滤，false：不过滤
     */

    protected boolean isFilter(V message) {

        return false;
    }

    /**
     * 异常时是否重复发送
     *
     * @return true: 消息重试，false：不重试
     */

    protected abstract boolean isRetry();

    /**
     * 消费异常时是否抛出异常
     *
     * @return true: 抛出异常，则由rocketmq机制自动重试；false：消费异常(如果没有开启重试则消息会被自动ack)
     */

    protected abstract boolean throwException();

    /**
     * 最大重试次数
     *
     * @return 最大重试次数，默认2次，再加载发送那次，一共3次
     */

    protected int maxRetryTimes() {
        return 2;
    }

    /**
     * 使用模板模式构建消息消费框架，可自由扩展或删减
     *
     * @param record
     */
    public void dispatchMessage(ConsumerRecord<K, V> record) {
        // 基础日志记录被父类处理了
        V message = record.value();
        log.info("消费者收到消息[{}]", message);
        if (this.isFilter(message)) {
            log.info("消息id{}不满足消费条件，已过滤。", record.key());
            return;
        }

        // 超过最大重试次数时调用子类方法处理
        if (message.getRetryTimes() > this.maxRetryTimes()) {
            this.handleMaxRetriesExceeded(message);
            return;
        }

        try {
            long now = Instant.now().toEpochMilli();
            this.handleMessage(message);
            long costTime = Instant.now().toEpochMilli() - now;
            log.info("消息{}消费成功，耗时[{}ms]", message.getKey(), costTime);
        } catch (Exception e) {
            log.error("消息{}消费异常", message.getKey(), e);
            // 是捕获异常还是抛出，由子类决定
            if (this.throwException()) {
                throw new RuntimeException(e);
            }
            // 异常时是否重复发送
            if (!this.isRetry()) {
                return;
            }
            // 此时如果不开启重试机制，则默认ACK了
            this.handleRetry(record);
        }
    }

    protected void handleRetry(ConsumerRecord<K, V> record) {
        //重新构建消息体
        V message = record.value();
        String messageSource = message.getSource();
        if (!messageSource.startsWith(RETRY_PREFIX)) {
            message.setSource(RETRY_PREFIX + messageSource);
        }
        message.setRetryTimes(message.getRetryTimes() + 1);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        try {
            // TODO 这里需要增加延迟消费
            scheduledExecutorService.schedule(() -> enhanceTemplate.getTemplate().send(record.topic(), message), 3, TimeUnit.SECONDS);
        } catch (Exception ex) {
            // 此处捕获之后，相当于此条消息被消息完成然后重新发送新的消息
            throw new RuntimeException(ex);
        } finally {
            scheduledExecutorService.shutdown();
        }
    }


}
