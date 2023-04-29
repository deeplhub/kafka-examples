# 消息重试与死信队列 - 注解


## 默认重试策略

默认情况下，spring-kafka 在消费逻辑抛出异常时，会快速重试 10 次（无间隔时间），如果重试完成后，依旧消费失败，spring-kafka 会 commit 这条记录。

> 默认重试的实现原理是：重置当前 consumer offset，感兴趣的同学可以在 SeekUtils#doSeeks debug 一下

可以通过自定义 SeekToCurrentErrorHandler 来控制消费失败后的处理逻辑。例如：添加重试间隔，重试完成后依旧失败的消息发送到 DLT


<br>
<br>



## 自定义 SeekToCurrentErrorHandler

Spring-Kafka 通过实现自定义的 SeekToCurrentErrorHandler ，当 Consumer 消费消息异常的时候，进行拦截处理：

重试小于最大次数时，重新投递该消息给 Consumer
重试到达最大次数时，如果Consumer 还是消费失败时，该消息就会发送到死信队列。 死信队列的 命名规则为： 原有 Topic + .DLT 后缀 = 其死信队列的 Topic

```
ConsumerRecordRecoverer recoverer = new DeadLetterPublishingRecoverer(template);
```

创建 DeadLetterPublishingRecoverer 对象，它负责实现，在重试到达最大次数时，Consumer 还是消费失败时，该消息就会发送到死信队列。

也可以选择 BackOff 的另一个子类 ExponentialBackOff 实现，提供指数递增的间隔时间

```
new SeekToCurrentErrorHandler(recoverer, backOff);
```

创建 SeekToCurrentErrorHandler 对象，负责处理异常，串联整个消费重试的整个过程。

<br>


在消息消费失败时，SeekToCurrentErrorHandler 会将调用 Kafka Consumer 的 seek(TopicPartition partition, long offset) 方法，将 Consumer 对于该消息对应的 TopicPartition 分区的本地进度设置成该消息的位置。

这样，Consumer 在下次从 Kafka Broker 拉取消息的时候，又能重新拉取到这条消费失败的消息，并且是第一条。

同时，Spring-Kafka 使用 FailedRecordTracker 对每个 Topic 的每个 TopicPartition 消费失败次数进行计数，这样相当于对该 TopicPartition 的第一条消费失败的消息的消费失败次数进行计数。

另外，在 FailedRecordTracker 中，会调用 BackOff 来进行计算，该消息的下一次重新消费的时间，通过 Thread#sleep(...) 方法，实现重新消费的时间间隔。

> 注意：

FailedRecordTracker 提供的计数是客户端级别的，重启 JVM 应用后，计数是会丢失的。所以，如果想要计数进行持久化，需要自己重新实现下 FailedRecordTracker 类，通过 ZooKeeper 存储计数。


SeekToCurrentErrorHandler 是只针对消息的单条消费失败的消费重试处理。如果想要有消息的批量消费失败的消费重试处理，可以使用 SeekToCurrentBatchErrorHandler 。配置方式如下：

```java
@Bean
@Primary
public BatchErrorHandler kafkaBatchErrorHandler() {
    // 创建 SeekToCurrentBatchErrorHandler 对象
    SeekToCurrentBatchErrorHandler batchErrorHandler = new SeekToCurrentBatchErrorHandler();
    // 创建 FixedBackOff 对象
    BackOff backOff = new FixedBackOff(10 * 1000L, 3L);
    batchErrorHandler.setBackOff(backOff);
    // 返回
    return batchErrorHandler;
}
```



<br>
<br>



## DLT死信队列

如果 3 次重试后依旧失败，会将消息发送到 DLT， 默认情况，消息被发送到死信队列后，会输出一条日志。DLT 的 topic 为原 topic 加上后缀“.DLT”。


<br>
<br>



## 重试注解

@Retryable 注解中几个参数的含义：

* value：抛出指定异常才会重试
* include：和value一样，默认为空，当exclude也为空时，默认所有异常
* exclude：指定不处理的异常
* maxAttempts：最大重试次数，默认3次
* backoff：重试等待策略，默认使用@Backoff，@Backoff的value默认为1000L，我们设置为2000L；
* multiplier（指定延迟倍数）默认为0，表示固定暂停1秒后进行重试，如果把multiplier设置为2，则第一次重试为2秒（delay 指定时间），第二次为4(multiplier X delay)秒，第三次为8(3 X multiplier X delay)秒。
